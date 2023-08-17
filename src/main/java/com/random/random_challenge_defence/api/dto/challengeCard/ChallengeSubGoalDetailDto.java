package com.random.random_challenge_defence.api.dto.challengeCard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChallengeSubGoalDetailDto {

    private Long id;
    private String subGoal;
}
