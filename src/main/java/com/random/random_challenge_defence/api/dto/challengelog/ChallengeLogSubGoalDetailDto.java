package com.random.random_challenge_defence.api.dto.challengelog;

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
