package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.memberpersonality.*;
import com.random.random_challenge_defence.api.service.MemberPersonalityService;
import com.random.random_challenge_defence.api.service.MemberService;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.membermemberpersonality.MemberMemberPersonality;
import com.random.random_challenge_defence.domain.memberpersonality.MemberPersonality;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-personality")
public class MemberPersonalityController {

    private final MemberPersonalityService memberPersonalityService;
    private final MemberService memberService;

    private final ResponseService responseService;


    @ApiOperation(value = "사용자 특성 조회", notes = "로그인 사용자가 선택한 특성 리스트를 조회합니다.")
    @GetMapping("/member-select")
    public CommonResponse<List<Long>> readPersonalities() {

        Member loginMember = memberService.getLoginMember();
        List<MemberMemberPersonality> memberMemberPersonalities = loginMember.getMemberMemberPersonalities();
        List<Long> collect = memberMemberPersonalities.stream().map(e -> e.getMemberPersonality().getId()).collect(Collectors.toList());

        return responseService.getResult(collect);
    }

    @ApiOperation(value = "사용자 특성 지정", notes = "로그인 사용자에게 기존 특성을 삭제하고 새로운 특성을 부여합니다.")
    @PostMapping("/member-assign")
    public CommonResponse assign(@RequestBody MemberPersonalityAssignDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        Member loginMember = memberService.findByEmail(memberEmail);

        memberPersonalityService.updatePersonalities(loginMember, form.getIds());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "사용자 특성 전체 리스트 조회", notes = "전체 사용자 특성 리스트를 조회합니다.")
    @GetMapping("/list")
    public CommonResponse<List<MemberPersonalityDetailDto>> readAll() {
        List<MemberPersonality> all = memberPersonalityService.findAll();
        List<MemberPersonalityDetailDto> memberPersonalityDetailDtos = all.stream().map(e -> e.toDetailDto()).collect(Collectors.toList());
        return responseService.getResult(memberPersonalityDetailDtos);
    }


    @ApiOperation(value = "사용자 특성 조회", notes = "사용자 특성을 조회합니다.")
    @GetMapping("/{id}")
    public CommonResponse<MemberPersonalityDetailDto> readOne(@PathVariable Long id) {
        MemberPersonalityDetailDto dto = memberPersonalityService.readOne(id).toDetailDto();
        return responseService.getResult(dto);
    }
}
