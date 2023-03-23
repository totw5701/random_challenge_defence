package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.advice.exception.CAccessDeniedException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeTryReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeTryUpdateDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallenge;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallengeStatus;
import com.random.random_challenge_defence.service.MemberChallengeService;
import com.random.random_challenge_defence.service.MemberService;
import com.random.random_challenge_defence.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class TryController {

    private final MemberChallengeService memberChallengeService;
    private final MemberService memberService;

    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 도전", notes = "챌린지에 도전한다.")
    @PostMapping
    public CommonResponse tryChallenge(@RequestBody ChallengeTryReqDto form) {
        MemberChallenge memberChallenge = memberChallengeService.create(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "챌린지 스킵", notes = "현 챌린지를 스킵한다.")
    @PutMapping("/skip")
    public void skipChallenge(@RequestBody Map<String, Long> payload) {
        MemberChallenge memberChallenge = memberChallengeService.read(payload.get("id"));

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByEmail(principal.getUsername());

        if(memberChallenge.getMember().getId() != member.getId()) {
            throw new CAccessDeniedException();
        }

        ChallengeTryUpdateDto updateDto = ChallengeTryUpdateDto.builder().id(memberChallenge.getId()).status(MemberChallengeStatus.PASS).build();
        MemberChallenge update = memberChallengeService.update(updateDto);
    }


}
