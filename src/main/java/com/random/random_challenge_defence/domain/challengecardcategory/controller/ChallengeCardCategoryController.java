package com.random.random_challenge_defence.domain.challengecardcategory.controller;

import com.random.random_challenge_defence.domain.challengecardcategory.dto.ChallengeCardCategoryDetailDto;
import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.challengecardcategory.service.ChallengeCardCategoryService;
import com.random.random_challenge_defence.global.result.ResponseService;
import com.random.random_challenge_defence.domain.challengecardcategory.entity.ChallengeCardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge-card-category")
public class ChallengeCardCategoryController {

    private final ChallengeCardCategoryService challengeCardCategoryService;
    private final ResponseService responseService;

    /**
     * 챌린지 카드 카테고리 리스트 조회
     */
    @GetMapping("/list")
    public CommonResponse<Page<ChallengeCardCategoryDetailDto>> list(@RequestParam(name = "nowPage", defaultValue = "0") Integer nowPage) {
        Page<ChallengeCardCategoryDetailDto> challengeCardCategoryDetailDtos = challengeCardCategoryService.readPageList(nowPage);
        return responseService.getResult(challengeCardCategoryDetailDtos);
    }

    @GetMapping("/{id}")
    public CommonResponse<ChallengeCardCategoryDetailDto> getOne(@PathVariable String id) {
        ChallengeCardCategory challengeCardCategory = challengeCardCategoryService.getEntityById(id);
        return responseService.getResult(challengeCardCategory.toDetailDto());
    }

}
