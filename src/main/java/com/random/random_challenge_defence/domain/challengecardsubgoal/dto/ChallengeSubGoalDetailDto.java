package com.random.random_challenge_defence.domain.challengecardsubgoal.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChallengeSubGoalDetailDto {

    @ApiModelProperty(example = "long 챌린지 카드 중간 도전 id")
    private Long id;
    @ApiModelProperty(example = "챌린지 카드 중간 도전 내용")
    private String subGoal;
}
