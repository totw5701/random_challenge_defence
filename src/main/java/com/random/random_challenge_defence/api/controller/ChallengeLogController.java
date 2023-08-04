package com.random.random_challenge_defence.api.controller;

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


    @ApiOperation(value = "챌린지 도전하기", notes = "챌린지에 도전합니다.")
    @PostMapping("/try")
    public CommonResponse<ChallengeLogDetailDto> tryChallenge(@RequestBody ChallengeLogReqDto form) {
        String memberEmail = memberService.getLoginUserEmail();
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
        Long challengeLogId = map.get("id");
        boolean isPass = challengeLogService.successValidate(challengeLogId);
        if(!isPass) {
            return responseService.getStringResult("result", "성공 요구조건을 만족하지 못하였습니다.");
        }
        challengeLogService.successChallengeLog(challengeLogId);
        return responseService.getSuccessResult();
    }

}
