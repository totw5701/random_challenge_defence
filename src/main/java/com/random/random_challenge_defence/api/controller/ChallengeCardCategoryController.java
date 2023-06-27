package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryReqDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.ChallengeCardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge-card-category")
public class ChallengeCardCategoryController {

    private final ChallengeCardCategoryService challengeCardCategoryService;

    @GetMapping("/create")
    public CommonResponse create(ChallengeCardCategoryReqDto form) {

        challengeCardCategoryService.createCategory(form);
        return null;
    }



}
