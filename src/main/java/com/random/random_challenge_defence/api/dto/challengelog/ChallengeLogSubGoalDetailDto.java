package com.random.random_challenge_defence.api.dto.challengelog;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeLogSubGoalDetailDto {

    @ApiModelProperty(example = "long 챌린지 이력 중간 도전 id")
    private Long id;
    @ApiModelProperty(example = "중간 도전 내용")
    private String subGoal;
    @ApiModelProperty(example = "중간 도전 상태")
    private String status;
}
