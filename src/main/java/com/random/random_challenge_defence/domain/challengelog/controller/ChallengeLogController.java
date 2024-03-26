package com.random.random_challenge_defence.domain.challengelog.controller;

import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.challengelog.entity.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.dto.*;
import com.random.random_challenge_defence.domain.challengelog.service.ChallengeLogService;
import com.random.random_challenge_defence.domain.challengelogsubgoal.entity.ChallengeLogSubGoal;
import com.random.random_challenge_defence.global.result.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-log")
public class ChallengeLogController {

    private final ChallengeLogService challengeLogService;

    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 로그 인증 업로드", notes = "업로드한 인증 파일을 챌린지 로그에 할당합니다.")
    @PutMapping("/evidence")
    public CommonResponse uploadEvidence(@RequestBody ChallengeLogEvidenceReqDto form) {
        challengeLogService.uploadEvidence(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "챌린지 스킵하기", notes = "도전 중인 챌린지를 스킵합니다.")
    @PutMapping("/skip")
    public CommonResponse<ChallengeLogDetailDto> skipChallenge(@RequestBody ChallengeLogReqDto form) {
        ChallengeLog challengeLog = challengeLogService.skipChallenge(form);
        return responseService.getResult(challengeLog.toDetailDto());
    }

    @ApiOperation(value = "챌린지 도전하기", notes = "챌린지 이력 테이블을 생성합니다. (최대5개)")
    @PostMapping("/try")
    public CommonResponse<ChallengeLogDetailDto> tryChallenge(@RequestBody ChallengeLogReqDto form) {
        ChallengeLog challengeLog = challengeLogService.tryChallenge(form);
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
        List<ChallengeLogDetailDto> logDtos = challengeLogService.getTryingChallengeLogDetailList();
        return responseService.getResult(logDtos);
    }

    @ApiOperation(value = "챌린지 이력 조회", notes = "현재 및 과거에 도전했던 챌린지 이력을 페이징하여 조회합니다.")
    @GetMapping("/list/my-logs")
    public CommonResponse<Page<ChallengeLogDetailDto>> challengeLogDetailList(@RequestBody ChallengeLogDetailPagingReqDto form) {
        Page<ChallengeLogDetailDto> challengeDtoPage = challengeLogService.getChallengeLogDetailList(form);
        return responseService.getResult(challengeDtoPage);
    }

    @ApiOperation(value = "중간 도전 상태 업데이트", notes = "챌린지 이력 중간 도전의 상태를 업데이트합니다.")
    @PutMapping("/sub-goal/update")
    public CommonResponse<ChallengeLogSubGoalDetailDto> changeSubGoalStatus(@RequestBody ChallengeLogSubGoalUpdateDto form) {
        ChallengeLogSubGoal result = challengeLogService.changeSubGoalStatus(form);
        return responseService.getResult(result.toDetail());
    }

    @ApiOperation(value = "챌린지 성공", notes = "챌린지 이력의 상태를 성공으로 업데이트합니다.")
    @PostMapping("/success")
    public CommonResponse successChallenge(@RequestBody ChallengeLogReqDto form) {
        challengeLogService.successChallenge(form);
        return responseService.getSuccessResult();
    }

}
