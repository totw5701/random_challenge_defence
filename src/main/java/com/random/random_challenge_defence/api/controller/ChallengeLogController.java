package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.api.dto.challengelog.*;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.*;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.member.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-log")
public class ChallengeLogController {

    private final ChallengeLogService challengeLogService;
    private final MemberService memberService;
    private final ChallengeCardService challengeCardService;
    private final ChallengeLogSubGoalService challengeLogSubGoalService;
    private final FileService fileService;

    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 로그 인증 업로드", notes = "업로드한 인증 파일을 챌린지 로그에 할당합니다.")
    @PutMapping("/evidence")
    public CommonResponse uploadChallengeEvidence(@RequestBody ChallengeLogEvidenceReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        // 로그인 사용자 본인의 challenge card가 아닐 경우 실패처리
        ChallengeLog challengeLog = challengeLogService.getEntityById(form.getChallengeLogId());
        if(!challengeLog.getMember().getEmail().equals(memberEmail)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }

        // 로그인 사용자 본인이 올린 file이 아닌 경우 실패처리
        List<File> fileListByIds = fileService.getEntityListByIds(form.getEvidenceIdList());
        for(File file : fileListByIds) {
            if(!file.getMember().getEmail().equals(memberEmail)) {
                throw new CustomException(ExceptionCode.ACCESS_DENIED);
            }
        }

        // 파일 할당
        for(File file : fileListByIds) {
            fileService.assignChallengeLog(file, challengeLog);
        }

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "챌린지 스킵하기", notes = "도전 중인 챌린지를 스킵합니다.")
    @PutMapping("/skip")
    public CommonResponse<ChallengeLogDetailDto> skipChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        // 본인의 challenge log가 아닐 경우 실패처리
        ChallengeLog challengeLog = challengeLogService.getEntityById(Long.valueOf(form.getChallengeId()));
        if(!challengeLog.getMember().getEmail().equals(memberEmail)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }

        // 챌린지 로그 스킵
        challengeLogService.skipChallengeLog(challengeLog);
        return responseService.getResult(challengeLog.toDetailDto());
    }

    @ApiOperation(value = "챌린지 도전하기", notes = "챌린지 이력 테이블을 생성합니다. (최대5개)")
    @PostMapping("/try")
    public CommonResponse<ChallengeLogDetailDto> tryChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        // 한번에 하나의 챌린지만 도전 가능
        Long numOfTrying = challengeLogService.getNumOfTrying(memberEmail);
        if(numOfTrying >= 2) {
            throw new CustomException(ExceptionCode.SERVICE_USAGE_LIMIT_EXCEEDED);
        }

        // PAUSE 했던 같은 challenge card에 대하여 다시 도전 할 시 지난 이력을 재활용 한다.
        Optional<ChallengeLog> opPausedChallengeLog = challengeLogService.getPausedChallengeLog(memberEmail, form.getChallengeId());
        if(opPausedChallengeLog.isPresent()) {
            ChallengeLog pausedChallengeLog = opPausedChallengeLog.get();
            challengeLogService.restartChallengeLog(pausedChallengeLog);
            return responseService.getResult(pausedChallengeLog.toDetailDto());
        }

        ChallengeCard challengeCard = challengeCardService.getEntityById(form.getChallengeId());
        Member member = memberService.getEntityById(memberEmail);
        ChallengeLog challengeLog = challengeLogService.createChallengeLog(member, challengeCard);
        return responseService.getResult(challengeLog.toDetailDto());
    }

    @ApiOperation(value = "챌린지 상세 조회", notes = "챌린지 이력 상세 내용을 조회합니다.")
    @GetMapping("/detail/{id}")
    public CommonResponse<ChallengeLogDetailDto> challengeLogDetail(@PathVariable("id") Long id) {
        ChallengeLog challengeLog = challengeLogService.getEntityById(id);
        return responseService.getResult(challengeLog.toDetailDto());
    }

    @ApiOperation(value = "도전중인 챌린지 이력 조회", notes = "현재 도전중인 챌린지 이력 리스트를 조회합니다.")
    @GetMapping("/list/my-logs/trying")
    public CommonResponse<List<ChallengeLogDetailDto>> challengeLogDetailListTrying() {
        String memberEmail = memberService.getLoginUserEmail();
        List<ChallengeLog> challengeLogs = challengeLogService.readPageListTrying(memberEmail);

        List<ChallengeLogDetailDto> logDtos = challengeLogs.stream()
                .map(log -> log.toDetailDto())
                .collect(Collectors.toList());

        return responseService.getResult(logDtos);
    }

    @ApiOperation(value = "챌린지 이력 조회", notes = "현재 및 과거에 도전했던 챌린지 이력을 페이징하여 조회합니다.")
    @GetMapping("/list/my-logs/{nowPage}")
    public CommonResponse<Page<ChallengeLogDetailDto>> challengeLogDetailList(@PathVariable(name = "nowPage") Integer nowPage) {
        String memberEmail = memberService.getLoginUserEmail();
        Page<ChallengeLog> challengeLogs = challengeLogService.readPageList(nowPage, memberEmail);

        List<ChallengeLogDetailDto> logDtos = challengeLogs.stream()
                .map(log -> log.toDetailDto())
                .collect(Collectors.toList());

        Page<ChallengeLogDetailDto> challengeDtoPage = new PageImpl<>(logDtos, challengeLogs.getPageable(), challengeLogs.getTotalElements());

        return responseService.getResult(challengeDtoPage);
    }

    @ApiOperation(value = "중간 도전 상태 업데이트", notes = "챌린지 이력 중간 도전의 상태를 업데이트합니다.")
    @PutMapping("/sub-goal/update")
    public CommonResponse<ChallengeLogSubGoalDetailDto> subGoalClear(@RequestBody ChallengeLogSubGoalUpdateDto reqDto) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLogSubGoal challengeLogSubGoal = challengeLogSubGoalService.getEntityById(reqDto.getId());

        if(!challengeLogSubGoal.getChallengeLog().getMember().getEmail().equals(memberEmail)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }

        ChallengeLogSubGoal result = challengeLogSubGoalService.updateSubGoal(challengeLogSubGoal, reqDto.getStatus());
        return responseService.getResult(result.toDetail());
    }

    @ApiOperation(value = "챌린지 성공", notes = "챌린지 이력의 상태를 성공으로 업데이트합니다.")
    @PostMapping("/success")
    public CommonResponse successChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        Long challengeLogId = form.getChallengeId();
        ChallengeLog challengeLog = challengeLogService.getEntityById(challengeLogId);

        if(!challengeLog.getMember().getEmail().equals(memberEmail)){
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }

        boolean isPass = challengeLogService.successValidate(challengeLog);
        if(!isPass) {
            throw new CustomException(ExceptionCode.SUCCESS_REQUIREMENTS_NOT_MET);
        }
        challengeLogService.successChallengeLog(challengeLog);
        return responseService.getSuccessResult();
    }

}
