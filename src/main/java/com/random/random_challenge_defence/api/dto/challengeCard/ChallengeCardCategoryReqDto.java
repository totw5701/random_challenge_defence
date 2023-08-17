package com.random.random_challenge_defence.api.dto.challengeCard;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ChallengeCardCategoryReqDto {

    private String title;
    private String description;
}
