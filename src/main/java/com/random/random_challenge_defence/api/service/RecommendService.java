package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardAssignScoreDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class RecommendService {

    private final ChallengeCardCategoryRepository challengeCardCategoryRepository;
    private final ChallengeCardRepository challengeCardRepository;

    public Long getRandomChallenge(Integer memberLevel) {
        // 챌린지 카테고리 임의 선택
        List<Long> categoryIds = challengeCardCategoryRepository.findAllId();
        int idx = new Random().nextInt(categoryIds.size());
        Long categoryId = categoryIds.get(idx);

        return getRandomChallengeByCategory(memberLevel, categoryId);
    }

    public Long getRandomChallengeByCategory(Integer memberLevel, Long challengeCardCategory) {
        // 특정 레벨 이하의, 특정 카테고리에 속한 챌린지 카들의 Id와 우선순위를 조회합니다.
        List<ChallengeCardAssignScoreDto> list = getIdAndAssignScoreByChallengeCardCategory(
                memberLevel, challengeCardCategory);

        // 우선순위가 높은 id에 가중치를 부여하여 랜덤으로 하나의 id를 추출합니다.
        return assignScoreFavorRandom(list);
    }

    public Long getRandomChallengeByPersonalities(Integer memberLevel, List<Long> personalityIds) {
        // 특정 레벨 이하, 특정 사용자 특성을 가진 챌린지 카드들의 Id와 우선순위를 조회합니다.
        List<ChallengeCardAssignScoreDto> list = getIdAndAssignScoreByPersonality(memberLevel, personalityIds);

        // 우선순위가 높은 id에 가중치를 부여하여 랜덤으로 하나의 id를 추출합니다.
        return assignScoreFavorRandom(list);
    }


    /**
     * 사용자가 사용가능한 챌린지들 중 assignScore가 높은 챌린지에 가중치를 주어 랜덤 챌린지를 반환한다
     */
    private Long assignScoreFavorRandom(List<ChallengeCardAssignScoreDto> list) {

        // assignScore 총합계산
        int sumAssignScore = 0;
        for(ChallengeCardAssignScoreDto dto : list){
            sumAssignScore += dto.getAssignScore();
        }

        // 랜덤한 챌린지 선택
        Random rand = new Random();
        int randValue = rand.nextInt(sumAssignScore);

        Long selectedChallenge = 0L;
        int calculativeScore = 0;
        for(ChallengeCardAssignScoreDto dto : list){
            calculativeScore += dto.getAssignScore();
            if(calculativeScore >= randValue) {
                selectedChallenge = dto.getChallengeCardId();
                break;
            }
        }

        return selectedChallenge;
    }

    /**
    * 사용자가 사용가능하며 특정 챌린지 카테고리에 속한 카드의 id와 assignScore를 반환한다.
    */
    private List<ChallengeCardAssignScoreDto> getIdAndAssignScoreByChallengeCardCategory(Integer memberLevel, Long challengeCardCategory) {
        return challengeCardRepository.findIdAndAssignScoreUnderMemberLeverByChallengeCardCategory(memberLevel, challengeCardCategory);
    }

    /**
     * 사용자가 사용이 가능하며 특정 특성을 가진 카드의 id와 assignScore를 반환한다.
     */
    private List<ChallengeCardAssignScoreDto> getIdAndAssignScoreByPersonality(Integer memberLevel, List<Long> personalityIds) {
        return challengeCardRepository.findIdAndAssignScoreUnderMemberLevelByPersonality(memberLevel, personalityIds);
    }
}
