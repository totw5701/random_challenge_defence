package com.random.random_challenge_defence.api.dto.challenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChallengeCardCategoryReqDto {

    private String title;
    private String description;
}
