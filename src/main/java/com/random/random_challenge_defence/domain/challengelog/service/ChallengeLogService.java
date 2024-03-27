package com.random.random_challenge_defence.domain.challengelog.service;

import com.random.random_challenge_defence.domain.challengecard.service.ChallengeCardService;
import com.random.random_challenge_defence.domain.challengelog.dto.*;
import com.random.random_challenge_defence.domain.challengelogsubgoal.service.ChallengeLogSubGoalService;
import com.random.random_challenge_defence.domain.file.service.FileService;
import com.random.random_challenge_defence.domain.member.service.MemberService;
import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.challengecard.entity.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecardsubgoal.entity.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelog.entity.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.repository.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.entity.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.repository.ChallengeLogSubGoalRepository;
import com.random.random_challenge_defence.domain.file.entity.File;
import com.random.random_challenge_defence.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ChallengeLogService {

    private final MemberService memberService;
    private final FileService fileService;
    private final ChallengeLogSubGoalService challengeLogSubGoalService;
    private final ChallengeCardService challengeCardService;

    private final ChallengeLogRepository challengeLogRepository;
    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    public ChallengeLog getEntityById(Long id) {
        Optional<ChallengeLog> byId = challengeLogRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_LOG);
        }
        return byId.get();
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

    private void createChallengeLogSubGoals(ChallengeCard challengeCard, ChallengeLog challengeLog) {
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
    }

    public void uploadEvidence(ChallengeLogEvidenceReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLog challengeLog = validateOwner(form.getChallengeLogId(), memberEmail);

        // 로그인 사용자 본인이 올린 file이 아닌 경우 실패처리
        List<File> files = fileService.validateOwner(form.getEvidenceIdList(), memberEmail);

        // 파일 할당
        files.forEach((file) -> file.assignChallengeLog(challengeLog));
    }

    private ChallengeLog validateOwner(Long challengeLogId, String memberEmail) {
        ChallengeLog challengeLog = getEntityById(challengeLogId);
        if(!challengeLog.getMember().getEmail().equals(memberEmail)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }
        return challengeLog;
    }

    public ChallengeLog skipChallenge(ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLog challengeLog = validateOwner(form.getChallengeCardId(), memberEmail);
        challengeLog.challengeSkip();
        return challengeLog;
    }

    public ChallengeLog tryChallenge(ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        memberService.verifyChallengeLogAvailability(memberEmail);
        return startChallenge(form.getChallengeCardId(), memberEmail);
    }

    private ChallengeLog startChallenge(Long challengeCardId, String memberEmail) {

        // 스킵했던 도전 이력이 존재하면 재도전 처리.
        Optional<ChallengeLog> opPausedChallengeLog = challengeLogRepository.findPausedLogByMemberEmailAndChallengeId(memberEmail, challengeCardId);
        if(opPausedChallengeLog.isPresent()) {
            ChallengeLog pausedChallengeLog = opPausedChallengeLog.get();
            pausedChallengeLog.challengeRetry();
            return pausedChallengeLog;
        } else {
            ChallengeCard challengeCard = challengeCardService.getEntityById(challengeCardId);
            Member member = memberService.getEntityById(memberEmail);
            return createChallengeLog(member, challengeCard);
        }
    }

    public List<ChallengeLogDetailDto> getTryingChallengeLogDetailList() {
        String memberEmail = memberService.getLoginUserEmail();
        List<ChallengeLog> challengeLogs = getTryingEntityList(memberEmail);

        return challengeLogs.stream()
                .map(ChallengeLog::toDetailDto)
                .collect(Collectors.toList());
    }

    private List<ChallengeLog> getTryingEntityList(String memberEmail) {
        return challengeLogRepository.findAllByEmailTrying(memberEmail);
    }

    public Page<ChallengeLogDetailDto> getChallengeLogDetailList(ChallengeLogDetailPagingReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        Pageable pageable = PageRequest.of(form.getCurrentPage(), form.getPageSize(), Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<ChallengeLog> challengeLogs = challengeLogRepository.findAllByEmail(memberEmail, pageable);

        List<ChallengeLogDetailDto> logDtos = challengeLogs.stream()
                .map(ChallengeLog::toDetailDto)
                .collect(Collectors.toList());

        return new PageImpl<>(logDtos, challengeLogs.getPageable(), challengeLogs.getTotalElements());
    }

    public ChallengeLogSubGoal changeSubGoalStatus(ChallengeLogSubGoalUpdateDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLogSubGoal challengeLogSubGoal = challengeLogSubGoalService.validateOwner(form.getId(), memberEmail);
        return challengeLogSubGoalService.updateSubGoal(challengeLogSubGoal, form.getStatus());
    }

    public void successChallenge(ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLog challengeLog = validateOwner(form.getChallengeCardId(), memberEmail);
        successValidate(challengeLog);
        successChallengeLog(challengeLog);
    }

    private void successChallengeLog(ChallengeLog challengeLog) {
        Member member = challengeLog.getMember();
        ChallengeCard challengeCard = challengeLog.getChallengeCard();
        member.increaseExperience(challengeCard.getExperience());
        challengeLog.challengeSuccess();
    }

    private void successValidate(ChallengeLog challengeLog) {
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
}
