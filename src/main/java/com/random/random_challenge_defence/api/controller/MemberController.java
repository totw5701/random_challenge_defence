package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import com.random.random_challenge_defence.api.dto.member.MemberSignUpReqDto;
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

    @PostMapping("/join")
    public CommonResponse<MemberDetailsDto> join(@Valid @RequestBody MemberSignUpReqDto form) {
        if(!form.getPassword().equals(form.getPassword2())){

        }
        Member member = memberService.join(form);
        return responseService.getResult(member.toDetailDto());
    }

    @GetMapping("/my-page/{id}")
    public CommonResponse<MemberDetailsDto> myInfo(@PathVariable String email) {
        if(email == null) {
            email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Member member = memberService.findByEmail(email);
        return responseService.getResult(member.toDetailDto());
    }

//    @PatchMapping("/my-page")
//    public CommonResponse<Map> updateMyInfo(@Valid @RequestBody MemberUpdateReqDto form) {
//        String memberId = memberService.update(form);
//        return responseService.getStringResult("memberId", memberId);
//    }
}
