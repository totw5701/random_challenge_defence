package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import com.random.random_challenge_defence.domain.member.Member;
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
public class ChallengeLogService {

    private final ChallengeLogRepository challengeLogRepository;
    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    public Page<ChallengeLog> readPageList(Integer nowPage, String memberEmail) {
        Pageable pageable = PageRequest.of(nowPage, 15, Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<ChallengeLog> challengeLogs = challengeLogRepository.findAllByEmail(memberEmail, pageable);
        return challengeLogs;
    }

    public ChallengeLog createChallengeLog(Member member, ChallengeCard challengeCard) {
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

    public List<ChallengeLogSubGoal> createChallengeLogSubGoals(ChallengeCard challengeCard, ChallengeLog challengeLog) {
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

    public ChallengeLog getChallengeLogDetail(Long id) {
        return challengeLogRepository.getById(id);
    }

    /**
     * 성공 가능 유효성 체크.
     * 1. 중간 목표 완료.
     * 2. 인증 완료.
     * @param challengeLogId
     */
    public boolean successValidate(Long challengeLogId) {
        boolean isPass = true;

        // 중간 목표 완료.
        List<ChallengeLogSubGoal> subGoals = challengeLogSubGoalRepository.getListByChallengeLogId(challengeLogId);
        for(ChallengeLogSubGoal subGoal: subGoals) {
            if(ChallengeLogSubGoalStatus.SUCCESS != subGoal.getChallengeLogSubGoalStatus()){
                isPass = false;
                break;
            };
        }

        // 인증 완료
        ChallengeLog challengeLog = challengeLogRepository.findById(challengeLogId).get();
        if(challengeLog.getEvidence() == null) {
            isPass = false;
        }
        return isPass;
    }

    @Transactional
    public void successChallengeLog(Long challengeLogId) {
        Optional<ChallengeLog> challengeLogOp = challengeLogRepository.findById(challengeLogId);
        if(!challengeLogOp.isPresent()) {
            throw new CNotFoundException("챌린지 도전 이력이 없습니다.");
        }
        challengeLogOp.get().challengeSuccess();
    }
}
