package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.advice.exception.CAccessDeniedException;
import com.random.random_challenge_defence.advice.exception.CChallengeLogTringFailureException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogReqDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalDetailDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalUpdateDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.*;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.member.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-log")
public class ChallengeLogController {

    private final ChallengeLogService challengeLogService;
    private final MemberService memberService;
    private final ChallengeCardService challengeCardService;
    private final ResponseService responseService;
    private final ChallengeLogSubGoalService challengeLogSubGoalService;

    @ApiOperation(value = "챌린지 스킵하기", notes = "도전 중인 챌린지를 스킵합니다.")
    @PostMapping("/skip")
    public CommonResponse<ChallengeLogDetailDto> skipChallenge(@RequestBody Map<String, String> map) {
        String memberEmail = memberService.getLoginUserEmail();
        ChallengeLog challengeLog = challengeLogService.getChallengeLogById(Long.valueOf(map.get("id")));
        if(!challengeLog.getMember().getEmail().equals(memberEmail)) {
            throw new CAccessDeniedException();
        }
        challengeLogService.skipChallengeLog(challengeLog);
        return responseService.getResult(challengeLog.toDetailDto());
    }

    @ApiOperation(value = "챌린지 도전하기", notes = "챌린지에 도전합니다.")
    @PostMapping("/try")
    public CommonResponse<ChallengeLogDetailDto> tryChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();

        Long numOfTrying = challengeLogService.getNumOfTrying(memberEmail);

        if(numOfTrying >= 5) {
            throw new CChallengeLogTringFailureException("최대 도전 갯수를 초과하였습니다.");
        }

        ChallengeCard challengeCard = challengeCardService.findById(form.getChallengeId());
        Member member = memberService.findByEmail(memberEmail);
        ChallengeLog challengeLog = challengeLogService.createChallengeLog(member, challengeCard);
        return responseService.getResult(challengeLog.toDetailDto());
    }

    @GetMapping("/detail/{id}")
    public CommonResponse<ChallengeLogDetailDto> challengeLogDetail(@PathVariable("id") Long id) {
        ChallengeLog challengeLogDetail = challengeLogService.getChallengeLogDetail(id);
        return responseService.getResult(challengeLogDetail.toDetailDto());
    }

    @GetMapping("/list/my-logs/trying")
    public CommonResponse<List<ChallengeLogDetailDto>> challengeLogDetailListTrying() {
        String memberEmail = memberService.getLoginUserEmail();
        List<ChallengeLog> challengeLogs = challengeLogService.readPageListTrying(memberEmail);

        List<ChallengeLogDetailDto> logDtos = challengeLogs.stream()
                .map(log -> log.toDetailDto())
                .collect(Collectors.toList());

        return responseService.getResult(logDtos);
    }

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

    @PostMapping("/sub-goal/update")
    public CommonResponse<ChallengeLogSubGoalDetailDto> subGoalClear(@RequestBody ChallengeLogSubGoalUpdateDto reqDto) {
        ChallengeLogSubGoal challengeLogSubGoal = challengeLogSubGoalService.updateSubGoal(reqDto);
        return responseService.getResult(challengeLogSubGoal.toDetail());
    }

    @PostMapping("/success")
    public CommonResponse successChallenge(@RequestBody Map<String, Long> map) {
        String memberEmail = memberService.getLoginUserEmail();
        Long challengeLogId = map.get("id");
        ChallengeLog challengeLog = challengeLogService.findById(challengeLogId);

        if(!challengeLog.getMember().getEmail().equals(memberEmail)){
            throw new CAccessDeniedException("권한 없는 접근입니다.");
        }

        boolean isPass = challengeLogService.successValidate(challengeLog);
        if(!isPass) {
            return responseService.getStringResult("result", "성공 요구조건을 만족하지 못하였습니다.");
        }
        challengeLogService.successChallengeLog(challengeLog);
        return responseService.getSuccessResult();
    }

}
