package com.random.random_challenge_defence.domain.challengecard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeCardCategoryRandomReqDto {

    private Integer memberLevel;
    private Long challengeCardCategory;
}
