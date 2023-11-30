package com.random.random_challenge_defence.api.dto.challengeCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChallengeDetailDto {

    @ApiModelProperty(example = "long 챌린지 카드 id")
    private Long id;
    @ApiModelProperty(example = "챌린지 카드 제목")
    private String title;
    @ApiModelProperty(example = "챌린지 카드 설명")
    private String description;
    @ApiModelProperty(example = "챌린지 카드 최종 목표")
    private String finalGoal;
    @ApiModelProperty(example = "int 챌린지 난이도")
    private Integer difficulty;
    @ApiModelProperty(example = "int 챌린지 우선 부여 점수")
    private Integer assignScore;
    @ApiModelProperty(example = "챌린지 생성일")
    private String createDtm;
    @ApiModelProperty(example = "달성 경험치")
    private Integer experience;
    private List<ChallengeSubGoalDetailDto> challengeSubGoals;
    private ChallengeCardCategoryDetailDto challengeCardCategory;
}
