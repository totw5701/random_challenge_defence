package com.random.random_challenge_defence.domain.member.controller;

import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.member.dto.MemberDetailsDto;
import com.random.random_challenge_defence.domain.member.dto.MemberPutReqDto;
import com.random.random_challenge_defence.domain.member.entity.Member;
import com.random.random_challenge_defence.domain.member.service.MemberService;
import com.random.random_challenge_defence.global.result.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보를 조회합니다.")
    @GetMapping("/{email}")
    public CommonResponse<MemberDetailsDto> userInfo(@PathVariable("email") String email) {
        Member member = memberService.getEntityById(email);
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

    @ApiOperation(value = "로그인 사용자 정보 조회", notes = "로그인 한 사용자의 정보를 조회합니다.")
    @GetMapping("/my-info")
    public CommonResponse<MemberDetailsDto> myInfo() {
        Member member =  memberService.getLoginMember();
        return responseService.getResult(member.toDetailDto());

    }
}
