package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardAssignScoreDto;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardCategoryRandomReqDto;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardPersonalityRandomReqDto;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardWholeRandomReqDto;
import com.random.random_challenge_defence.api.service.MemberService;
import com.random.random_challenge_defence.api.service.RecommendService;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.domain.member.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
public class ChallengeRecommendController {

    private final RecommendService recommendService;
    private final ResponseService responseService;

    @ApiOperation(value = "랜덤 챌린지 추천", notes = "사용 가능한 챌린지 중 우선순위에 가중치를 주어 임의의 챌린지 카드를 추천합니다.")
    @PostMapping("/whole-random")
    public CommonResponse<String> totalRandom(@RequestBody ChallengeCardWholeRandomReqDto form) {

        // 챌린지 카테고리 임의 선택
        List<Long> categoryIds = recommendService.getRandomCategoryId();
        int idx = new Random().nextInt(categoryIds.size());
        Long categoryId = categoryIds.get(idx);

        // 특정 레벨 이하의, 특정 카테고리에 속한 챌린지 카드들의 Id와 우선순위를 조회합니다.
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByChallengeCardCategory(
                form.getMemberLevel(), categoryId);

        // 우선순위가 높은 id에 가중치를 부여하여 랜덤으로 하나의 id를 추출합니다.
        Long recommendChallengeCardId = recommendService.assignScoreFavorRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));
    }

    @ApiOperation(value = "챌린지 카테고리 기반 랜덤 챌린지 추천", notes = "챌린지 카테고리에 기반하여 사용 가능한 챌린지 중 우선순위가 높은 챌린지 카드를 우선으로 추천합니다.")
    @PostMapping("/category-random")
    public CommonResponse<String> assignScoreFavorFilteredCategory(@RequestBody ChallengeCardCategoryRandomReqDto form) {
        // 특정 레벨 이하의, 특정 카테고리에 속한 챌린지 카들의 Id와 우선순위를 조회합니다.
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByChallengeCardCategory(
                form.getMemberLevel(), form.getChallengeCardCategory());

        // 우선순위가 높은 id에 가중치를 부여하여 랜덤으로 하나의 id를 추출합니다.
        Long recommendChallengeCardId = recommendService.assignScoreFavorRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));
    }

    @ApiOperation(value = "특성 기반 랜덤 챌린지 추천", notes = "특성에 기반하여 사용 가능한 챌린지 중 우선순위가 높은 챌린지 카드를 우선으로 추천합니다.")
    @PostMapping("/personality-random")
    public CommonResponse<String> assignScoreFavorFilteredPersonality(@RequestBody ChallengeCardPersonalityRandomReqDto form) {
        // 특정 레벨 이하의, 특정 특성을 가진 챌린지 카드들의 Id와 우선순위를 조회합니다.
        List<ChallengeCardAssignScoreDto> list = recommendService.getIdAndAssignScoreByPersonality(form.getMemberLevel(), form.getPersonalityIds());

        // 우선순위가 높은 id에 가중치를 부여하여 랜덤으로 하나의 id를 추출합니다.
        Long recommendChallengeCardId = recommendService.assignScoreFavorRandom(list);
        return responseService.getResult(String.valueOf(recommendChallengeCardId));
    }


}
