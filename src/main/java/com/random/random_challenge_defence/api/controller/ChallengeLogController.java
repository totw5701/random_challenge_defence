package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.advice.exception.CAccessDeniedException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogUpdateDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.service.ChallengeLogService;
import com.random.random_challenge_defence.service.MemberService;
import com.random.random_challenge_defence.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeLogController {

    private final ChallengeLogService challengeLogService;
    private final MemberService memberService;

    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 도전", notes = "챌린지에 도전한다.")
    @PostMapping
    public CommonResponse tryChallenge(@RequestBody ChallengeLogReqDto form) {
        ChallengeLogDetailDto challengeLogDetailDto = challengeLogService.create(form);
        return responseService.getResult(challengeLogDetailDto);
    }

    @ApiOperation(value = "챌린지 스킵", notes = "현 챌린지를 스킵한다.")
    @PutMapping("/skip")
    public CommonResponse<ChallengeLogDetailDto> skipChallenge(@RequestBody Map<String, Long> payload) {
        ChallengeLogDetailDto challengeLog = challengeLogService.read(payload.get("id"));

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByEmail(principal.getUsername());

        if (challengeLog.getMemberId() != member.getId()) {
            throw new CAccessDeniedException();
        }

        ChallengeLogUpdateDto updateDto = ChallengeLogUpdateDto.builder().id(challengeLog.getId()).status(ChallengeLogStatus.PASS).build();
        ChallengeLog update = challengeLogService.update(updateDto);
        return responseService.getResult(update.toDetailDto());
    }

    @ApiOperation(value = "도전 조회", notes = "도전 상세 정보 조회")
    @GetMapping("/{id}")
    public CommonResponse<ChallengeLogDetailDto> readTry(@PathVariable Long id) {
        ChallengeLogDetailDto challengeLog = challengeLogService.read(id);
        return responseService.getResult(challengeLog);
    }


}
