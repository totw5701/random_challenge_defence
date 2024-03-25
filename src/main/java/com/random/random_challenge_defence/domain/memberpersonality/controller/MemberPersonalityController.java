package com.random.random_challenge_defence.domain.memberpersonality.controller;

import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.memberpersonality.service.MemberPersonalityService;
import com.random.random_challenge_defence.global.result.ResponseService;
import com.random.random_challenge_defence.domain.memberpersonality.dto.MemberPersonalityAssignDto;
import com.random.random_challenge_defence.domain.memberpersonality.dto.MemberPersonalityDetailDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-personality")
public class MemberPersonalityController {

    private final MemberPersonalityService memberPersonalityService;

    private final ResponseService responseService;


    @ApiOperation(value = "사용자 특성 조회", notes = "로그인 사용자가 선택한 특성 리스트를 조회합니다.")
    @GetMapping("/member-select")
    public CommonResponse<List<Long>> getPersonalities() {
        List<Long> memberPersonalityIds = memberPersonalityService.getMemberPersonalities();
        return responseService.getResult(memberPersonalityIds);
    }

    @ApiOperation(value = "사용자 특성 지정", notes = "로그인 사용자에게 기존 특성을 삭제하고 새로운 특성을 부여합니다.")
    @PostMapping("/member-assign")
    public CommonResponse assign(@RequestBody MemberPersonalityAssignDto form) {
        memberPersonalityService.assignPersonalities(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "사용자 특성 전체 리스트 조회", notes = "전체 사용자 특성 리스트를 조회합니다.")
    @GetMapping("/list")
    public CommonResponse<List<MemberPersonalityDetailDto>> readAll() {
        List<MemberPersonalityDetailDto> memberPersonalityDetailDtos = memberPersonalityService.getPersonalities();
        return responseService.getResult(memberPersonalityDetailDtos);
    }

    @ApiOperation(value = "사용자 특성 조회", notes = "사용자 특성을 조회합니다.")
    @GetMapping("/{id}")
    public CommonResponse<MemberPersonalityDetailDto> readOne(@PathVariable Long id) {
        MemberPersonalityDetailDto dto = memberPersonalityService.getEntityById(id).toDetailDto();
        return responseService.getResult(dto);
    }
}
