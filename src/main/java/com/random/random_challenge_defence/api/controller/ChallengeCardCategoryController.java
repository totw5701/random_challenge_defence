package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeCardCategoryDetailDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeCardCategoryReqDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeCardCategoryUpdateDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.ChallengeCardCategoryService;
import com.random.random_challenge_defence.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge-card-category")
public class ChallengeCardCategoryController {

    private final ChallengeCardCategoryService challengeCardCategoryService;
    private final ResponseService responseService;

    @PostMapping("/create")
    public CommonResponse create(@RequestBody ChallengeCardCategoryReqDto form) {
        challengeCardCategoryService.createCategory(form);
        return responseService.getSuccessResult();
    }

    @GetMapping("/list")
    public CommonResponse list(@RequestParam(name = "nowPage", defaultValue = "0") Integer nowPage) {
        Page<ChallengeCardCategoryDetailDto> challengeCardCategoryDetailDtos = challengeCardCategoryService.readPageList(nowPage);
        return responseService.getResult(challengeCardCategoryDetailDtos);
    }

    @PostMapping("/update")
    public CommonResponse update(@RequestBody ChallengeCardCategoryUpdateDto form) {
        challengeCardCategoryService.updateCategory(form);
        return responseService.getSuccessResult();
    }

    @GetMapping("/{id}")
    public CommonResponse getOne(@PathVariable String id) {
        ChallengeCardCategoryDetailDto challengeCardCategoryDetailDto = challengeCardCategoryService.readOne(id);
        return responseService.getResult(challengeCardCategoryDetailDto);
    }

}
