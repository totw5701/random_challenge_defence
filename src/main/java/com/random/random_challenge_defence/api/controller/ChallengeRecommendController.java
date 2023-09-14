package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardAssignScoreDto;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardCategoryRandomReqDto;
import com.random.random_challenge_defence.api.service.RecommendService;
import com.random.random_challenge_defence.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
public class ChallengeRecommendController {

    private final RecommendService recommendService;
    private final ResponseService responseService;

    @PostMapping("/available-random")
    public CommonResponse<String> totalRandom(@RequestBody ChallengeCardCategoryRandomReqDto form) {
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByChallengeCardCategory(
                form.getMemberLevel(), form.getChallengeCardCategory());

        Long recommendChallengeCardId = recommendService.availableRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));
    }

    @PostMapping("/available-assign-score-favor-random")
    public CommonResponse<String> assignScoreFavor(@RequestBody ChallengeCardCategoryRandomReqDto form) {
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByChallengeCardCategory(
                form.getMemberLevel(), form.getChallengeCardCategory());

        Long recommendChallengeCardId = recommendService.assignScoreFavorRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));

    }
}
