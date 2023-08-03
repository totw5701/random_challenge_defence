package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import com.random.random_challenge_defence.api.dto.member.MemberPutReqDto;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.api.service.MemberService;
import com.random.random_challenge_defence.api.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원가입", notes = "새로운 사용자를 등록합니다.")
    @PostMapping
    public CommonResponse<MemberDetailsDto> join(@Valid @RequestBody MemberPutReqDto form) {
        Member member = memberService.join(form);
        return responseService.getResult(member.toDetailDto());
    }

    @ApiOperation(value = "user info 조회", notes = "사용자 정보를 조회합니다.")
    @GetMapping("/{email}")
    public CommonResponse<MemberDetailsDto> userInfo(@PathVariable("email") String email) {
        Member member = memberService.findByEmail(email);
        return responseService.getResult(member.toDetailDto());
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보를 수정합니다.")
    @PutMapping
    public CommonResponse<Member> updateMyInfo(@Valid @RequestBody MemberPutReqDto form) {
        Member member = memberService.update(form);
        return responseService.getResult(member);
    }

    @ApiOperation(value = "회원탈퇴", notes = "사용자를 삭제합니다.")
    @DeleteMapping
    public CommonResponse deleteMember(@RequestBody Long memberId){
        memberService.delete(memberId);
        return responseService.getSuccessResult();
    }

    @GetMapping("/my-info")
    public CommonResponse<MemberDetailsDto> myInfo() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.findByEmail(email);
        return responseService.getResult(member.toDetailDto());

    }


}
