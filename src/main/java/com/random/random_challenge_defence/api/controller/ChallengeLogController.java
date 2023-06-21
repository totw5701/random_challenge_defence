package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogReqDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.*;
import com.random.random_challenge_defence.domain.challengeCard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-log")
public class ChallengeLogController {

    private final ChallengeLogService challengeLogService;
    private final MemberService memberService;
    private final ChallengeCardService challengeCardService;
    private final ResponseService responseService;
    private final ChallengeLogSubGoalService challengeLogSubGoalService;


    @PostMapping("/try")
    public CommonResponse tryChallenge(@RequestBody ChallengeLogReqDto form) {
        Member member = memberService.findById(form.getMemberId());
        ChallengeCard challengeCard = challengeCardService.findById(form.getChallengeId());
        challengeLogService.createChallengeLog(member, challengeCard);
        return responseService.getSuccessResult();
    }

    @GetMapping("/detail/{id}")
    public CommonResponse<ChallengeLogDetailDto> challengeLogDetail(@PathVariable("id") Long id) {
        ChallengeLog challengeLogDetail = challengeLogService.getChallengeLogDetail(id);
        return responseService.getResult(challengeLogDetail.toDetailDto());
    }

    @PostMapping("/sub-goal/success")
    public CommonResponse subGoalClear(@RequestBody Map<String, Long> map) {
        challengeLogSubGoalService.successSubGoal(map.get("id"));
        return responseService.getSuccessResult();
    }

}
