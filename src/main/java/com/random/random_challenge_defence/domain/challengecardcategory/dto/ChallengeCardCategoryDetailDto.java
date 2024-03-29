package com.random.random_challenge_defence.domain.challengecardcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChallengeCardCategoryDetailDto {

    private Long id;
    private String title;
    private String description;

}
