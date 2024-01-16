package com.random.random_challenge_defence.domain.challengelog.controller;

import com.random.random_challenge_defence.domain.challengelogsubgoal.service.ChallengeLogSubGoalService;
import com.random.random_challenge_defence.domain.file.service.FileService;
import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.challengelog.entity.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.dto.*;
import com.random.random_challenge_defence.domain.challengelog.service.ChallengeLogService;
import com.random.random_challenge_defence.domain.challengelogsubgoal.entity.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.file.entity.File;
import com.random.random_challenge_defence.domain.member.service.MemberService;
import com.random.random_challenge_defence.global.result.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-log")
public class ChallengeLogController {

    private final ChallengeLogService challengeLogService;
    private final MemberService memberService;
    private final ChallengeLogSubGoalService challengeLogSubGoalService;
    private final FileService fileService;

    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 로그 인증 업로드", notes = "업로드한 인증 파일을 챌린지 로그에 할당합니다.")
    @PutMapping("/evidence")
    public CommonResponse uploadChallengeEvidence(@RequestBody ChallengeLogEvidenceReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        ChallengeLog challengeLog = challengeLogService.validateOwner(form.getChallengeLogId(), memberEmail);

        // 로그인 사용자 본인이 올린 file이 아닌 경우 실패처리
        List<File> files = fileService.validateOwner(form.getEvidenceIdList(), memberEmail);

        // 파일 할당
        challengeLogService.assignEvidence(challengeLog, files);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "챌린지 스킵하기", notes = "도전 중인 챌린지를 스킵합니다.")
    @PutMapping("/skip")
    public CommonResponse<ChallengeLogDetailDto> skipChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        ChallengeLog challengeLog = challengeLogService.validateOwner(form.getChallengeCardId(), memberEmail);

        // 챌린지 로그 스킵
        challengeLogService.skipChallengeLog(challengeLog);

        return responseService.getResult(challengeLog.toDetailDto());
    }

    @ApiOperation(value = "챌린지 도전하기", notes = "챌린지 이력 테이블을 생성합니다. (최대5개)")
    @PostMapping("/try")
    public CommonResponse<ChallengeLogDetailDto> tryChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        memberService.verifyChallengeLogAvailability(memberEmail);

        // PAUSE 했던 같은 challenge card에 대하여 다시 도전 할 시 지난 이력을 재활용 한다.
        ChallengeLog challengeLog = challengeLogService.startChallenge(form.getChallengeCardId(), memberEmail);
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
        List<ChallengeLog> challengeLogs = challengeLogService.getTryingEntityList(memberEmail);

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
    public CommonResponse<ChallengeLogSubGoalDetailDto> subGoalClear(@RequestBody ChallengeLogSubGoalUpdateDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        ChallengeLogSubGoal challengeLogSubGoal = challengeLogSubGoalService.validateOwner(form.getId(), memberEmail);

        ChallengeLogSubGoal result = challengeLogSubGoalService.updateSubGoal(challengeLogSubGoal, form.getStatus());
        return responseService.getResult(result.toDetail());
    }

    @ApiOperation(value = "챌린지 성공", notes = "챌린지 이력의 상태를 성공으로 업데이트합니다.")
    @PostMapping("/success")
    public CommonResponse successChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLog challengeLog = challengeLogService.validateOwner(form.getChallengeCardId(), memberEmail);
        challengeLogService.successValidate(challengeLog);
        challengeLogService.successChallengeLog(challengeLog);
        return responseService.getSuccessResult();
    }

}
