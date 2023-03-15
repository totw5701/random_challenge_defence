package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import com.random.random_challenge_defence.api.dto.member.MemberPutReqDto;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.service.MemberService;
import com.random.random_challenge_defence.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /**
     * 사용자 생성 및 수정 api
     * password1 password2 비교는 프론트 단에서
     * @param form
     * @return
     */
    @PostMapping
    public CommonResponse<MemberDetailsDto> join(@Valid @RequestBody MemberPutReqDto form) {
        Member member = memberService.join(form);
        return responseService.getResult(member.toDetailDto());
    }

    @GetMapping("/{id}")
    public CommonResponse<MemberDetailsDto> myInfo(@PathVariable("id") String email) {
        if(email == null) {
            email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Member member = memberService.findByEmail(email);
        return responseService.getResult(member.toDetailDto());
    }

    @PutMapping
    public CommonResponse<Member> updateMyInfo(@Valid @RequestBody MemberPutReqDto form) {
        Member member = memberService.update(form);
        return responseService.getResult(member);
    }

    @DeleteMapping
    public CommonResponse deleteMember(@RequestBody Long memberId){
        memberService.delete(memberId);
        return responseService.getSuccessResult();
    }
}
