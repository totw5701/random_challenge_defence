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
        Long randomChallengeCardId = recommendService.getRandomChallenge(form.getMemberLevel());
        return responseService.getResult(String.valueOf(randomChallengeCardId));
    }

    @ApiOperation(value = "챌린지 카테고리 기반 랜덤 챌린지 추천", notes = "챌린지 카테고리에 기반하여 사용 가능한 챌린지 중 우선순위가 높은 챌린지 카드를 우선으로 추천합니다.")
    @PostMapping("/category-random")
    public CommonResponse<String> assignScoreFavorFilteredCategory(@RequestBody ChallengeCardCategoryRandomReqDto form) {
        Long randomChallenge = recommendService.getRandomChallengeByCategory(form.getMemberLevel(), form.getChallengeCardCategory());
        return responseService.getResult(String.valueOf(randomChallenge));
    }

    @ApiOperation(value = "특성 기반 랜덤 챌린지 추천", notes = "특성에 기반하여 사용 가능한 챌린지 중 우선순위가 높은 챌린지 카드를 우선으로 추천합니다.")
    @PostMapping("/personality-random")
    public CommonResponse<String> assignScoreFavorFilteredPersonality(@RequestBody ChallengeCardPersonalityRandomReqDto form) {
        Long randomChallenge = recommendService.getRandomChallengeByPersonalities(form.getMemberLevel(), form.getPersonalityIds());
        return responseService.getResult(String.valueOf(randomChallenge));
    }


}
