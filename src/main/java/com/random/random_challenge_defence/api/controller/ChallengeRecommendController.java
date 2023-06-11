package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.recommend.AvailableRandomReqDto;
import com.random.random_challenge_defence.api.service.RecommendService;
import com.random.random_challenge_defence.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
public class ChallengeRecommendController {

    private final RecommendService recommendService;
    private final ResponseService responseService;

    @PostMapping("/available-random")
    public CommonResponse<String> totalRandom(@RequestBody AvailableRandomReqDto form) {

        Long recommendChallengeId = recommendService.availableRandom(form.getMemberLevel());
        return responseService.getResult(String.valueOf(recommendChallengeId));
    }
}
