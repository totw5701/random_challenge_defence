package com.random.random_challenge_defence.domain.challengecard.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChallengePutReqDto {

    private Long id;
    private String title;
    private String description;
    private String finalGoal;
    private Integer difficulty;
    private Integer assignScore;
    private List<String> challengeSubGoals;
    private Long challengeCardCategoryId;
    private Integer experience;
}
