package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardAssignScoreDto;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardCategoryRandomReqDto;
import com.random.random_challenge_defence.api.service.RecommendService;
import com.random.random_challenge_defence.api.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
public class ChallengeRecommendController {

    private final RecommendService recommendService;
    private final ResponseService responseService;

    @ApiOperation(value = "랜덤 챌린지 추천", notes = "사용 가능한 챌린지 중 임의의 챌린지 카드를 추천합니다.")
    @PostMapping("/available-random")
    public CommonResponse<String> totalRandom(@RequestBody ChallengeCardCategoryRandomReqDto form) {
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByChallengeCardCategory(
                form.getMemberLevel(), form.getChallengeCardCategory());

        Long recommendChallengeCardId = recommendService.availableRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));
    }

    @ApiOperation(value = "우선순위 랜덤 챌린지 추천", notes = "사용 가능한 챌린지 중 우선순위가 높은 챌린지 카드를 우선으로 추천합니다.")
    @PostMapping("/available-assign-score-favor-random")
    public CommonResponse<String> assignScoreFavor(@RequestBody ChallengeCardCategoryRandomReqDto form) {
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByChallengeCardCategory(
                form.getMemberLevel(), form.getChallengeCardCategory());

        Long recommendChallengeCardId = recommendService.assignScoreFavorRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));

    }
}
