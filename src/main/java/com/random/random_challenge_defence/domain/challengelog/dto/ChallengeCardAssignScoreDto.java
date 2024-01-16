package com.random.random_challenge_defence.domain.challengelog.dto;

import lombok.Data;

@Data
public class ChallengeCardAssignScoreDto {

    private Long challengeCardId;
    private Integer assignScore;

    public ChallengeCardAssignScoreDto(Long challengeCardId, Integer assignScore) {
        this.challengeCardId = challengeCardId;
        this.assignScore = assignScore;
    }
}
