package com.random.random_challenge_defence.api.dto.challengecardfeedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeCardFeedbackReqDto {

    @ApiModelProperty(example = "feedback id")
    private Long id;

    @ApiModelProperty(example = "챌린지 카드 id")
    private Long challengeCardId;

    @ApiModelProperty(example = "평점 1 ~ 10")
    @Max(10) @Min(1)
    private int rating;

    @ApiModelProperty(example = "평가")
    private String review;
}
