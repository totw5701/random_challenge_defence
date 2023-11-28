package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ChallengeLogService {

    private final ChallengeLogRepository challengeLogRepository;
    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;
    private final ChallengeCardRepository challengeCardRepository;
    private final MemberRepository memberRepository;

    public ChallengeLog startChallenge(Long challengeCardId, String memberEmail) {

        // 스킵했던 도전 이력이 존재하면 재도전 처리.
        Optional<ChallengeLog> opPausedChallengeLog = getPausedChallengeLog(memberEmail, challengeCardId);
        if(opPausedChallengeLog.isPresent()) {
            ChallengeLog pausedChallengeLog = opPausedChallengeLog.get();
            pausedChallengeLog.challengeRetry();
            return pausedChallengeLog;
        }

        Optional<ChallengeCard> opChallengeCard = challengeCardRepository.findById(challengeCardId);
        if(!opChallengeCard.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CARD);
        }

        Optional<Member> opMember = memberRepository.findByEmail(memberEmail);
        if(!opMember.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_MEMBER);
        }

        return createChallengeLog(opMember.get(), opChallengeCard.get());
    }

    public void assignEvidence(ChallengeLog challengeLog, List<File> files) {
        for(File file : files) {
            file.assignChallengeLog(challengeLog);
        }
    }

    public ChallengeLog validateOwner(Long challengeLogId, String memberEmail) {
        ChallengeLog challengeLog = getEntityById(challengeLogId);
        if(!challengeLog.getMember().getEmail().equals(memberEmail)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }
        return challengeLog;
    }

    public List<ChallengeLog> getTryingEntityList(String memberEmail) {
        List<ChallengeLog> challengeLogs = challengeLogRepository.findAllByEmailTrying(memberEmail);
        return challengeLogs;
    }

    public Page<ChallengeLog> readPageList(Integer nowPage, String memberEmail) {
        Pageable pageable = PageRequest.of(nowPage, 15, Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<ChallengeLog> challengeLogs = challengeLogRepository.findAllByEmail(memberEmail, pageable);
        return challengeLogs;
    }

    public void successValidate(ChallengeLog challengeLog) {
        // 이미 성공한 도전인지 확인.
        if(ChallengeLogStatus.SUCCESS == challengeLog.getStatus()) {
            throw new CustomException(ExceptionCode.SUCCESS_REQUIREMENTS_NOT_MET);
        }

        // 중간 목표 완료.
        List<ChallengeLogSubGoal> subGoals = challengeLogSubGoalRepository.getListByChallengeLogId(challengeLog.getId());
        for(ChallengeLogSubGoal subGoal: subGoals) {
            if(ChallengeLogSubGoalStatus.SUCCESS != subGoal.getChallengeLogSubGoalStatus()){
                throw new CustomException(ExceptionCode.SUCCESS_REQUIREMENTS_NOT_MET);
            };
        }

        // 인증 완료
        if(challengeLog.getEvidenceImages().size() == 0) {
            throw new CustomException(ExceptionCode.SUCCESS_REQUIREMENTS_NOT_MET);
        }
    }

    public void successChallengeLog(ChallengeLog challengeLog) {
        Member member = challengeLog.getMember();
        ChallengeCard challengeCard = challengeLog.getChallengeCard();
        member.increaseExperience(challengeCard.getExperience());
        challengeLog.challengeSuccess();
    }

    public void skipChallengeLog(ChallengeLog challengeLog) {
        challengeLog.challengeSkip();
    }

    public ChallengeLog getEntityById(Long id) {
        Optional<ChallengeLog> byId = challengeLogRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_LOG);
        }
        return byId.get();
    }

    private Optional<ChallengeLog> getPausedChallengeLog(String memberEmail, Long challengeId) {
        return challengeLogRepository.findPausedLogByMemberEmailAndChallengeId(memberEmail, challengeId);
    }

    private ChallengeLog createChallengeLog(Member member, ChallengeCard challengeCard) {
        ChallengeLog challengeLog = ChallengeLog.builder()
                .challengeCard(challengeCard)
                .status(ChallengeLogStatus.READY)
                .member(member)
                .startDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .build();

        ChallengeLog saved = challengeLogRepository.save(challengeLog);

        createChallengeLogSubGoals(challengeCard, challengeLog);

        return saved;
    }

    private List<ChallengeLogSubGoal> createChallengeLogSubGoals(ChallengeCard challengeCard, ChallengeLog challengeLog) {
        List<ChallengeCardSubGoal> subGoals = challengeCard.getChallengeCardSubGoals();
        List<ChallengeLogSubGoal> logSubGoals = new ArrayList<>();

        for(ChallengeCardSubGoal subGoal : subGoals) {
            ChallengeLogSubGoal challengeLogSubGoal = ChallengeLogSubGoal.builder()
                    .challengeLog(challengeLog)
                    .challengeCardSubGoal(subGoal)
                    .challengeLogSubGoalStatus(ChallengeLogSubGoalStatus.READY)
                    .build();
            logSubGoals.add(challengeLogSubGoalRepository.save(challengeLogSubGoal));
        }
        challengeLog.setChallengeLogSubGoals(logSubGoals);
        return logSubGoals;
    }
}
