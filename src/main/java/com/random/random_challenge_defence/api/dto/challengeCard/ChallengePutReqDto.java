package com.random.random_challenge_defence.api.dto.challengeCard;

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
    private String evidenceType;
    private Integer difficulty;
    private Integer assignScore;
    private List<String> challengeSubGoals;
    private Long challengeCardCategoryId;
    private Long image;
}
