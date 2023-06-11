package com.random.random_challenge_defence.api.dto.challenge;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeLogSubGoalDetailDto {

    private Long id;
    private String subGoal;
    private String status;
}
