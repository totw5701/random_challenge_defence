package com.random.random_challenge_defence.domain.challengelog.dto;

import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import lombok.Data;

@Data
public class ChallengeLogSubGoalUpdateDto {

    private Long id;
    private ChallengeLogSubGoalStatus status;
}
