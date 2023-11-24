package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardAssignScoreDto;
import com.random.random_challenge_defence.api.dto.recommend.ChallengeCardCategoryRandomReqDto;
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

    /**
     * 사용자가 사용가능한 챌린지들 중 assignScore가 높은 챌린지에 가중치를 주어 랜덤 챌린지를 반환한다
     */
    public Long assignScoreFavorRandom(List<ChallengeCardAssignScoreDto> list) {

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
    public List<ChallengeCardAssignScoreDto> getIdAndAssignScoreByChallengeCardCategory(Integer memberLevel, Long challengeCardCategory) {
        return challengeCardRepository.findIdAndAssignScoreUnderMemberLeverByChallengeCardCategory(memberLevel, challengeCardCategory);
    }

    public List<Long> getRandomCategoryId() {
        return challengeCardCategoryRepository.findAllId();
    }

    /**
     * 사용자가 사용이 가능하며 특정 특성을 가진 카드의 id와 assignScore를 반환한다.
     */
    public List<ChallengeCardAssignScoreDto> getIdAndAssignScoreByPersonality(Integer memberLevel, List<Long> personalityIds) {
        return challengeCardRepository.findIdAndAssignScoreUnderMemberLevelByPersonality(memberLevel, personalityIds);
    }
}
