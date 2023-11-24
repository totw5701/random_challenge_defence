package com.random.random_challenge_defence.api.dto.recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeCardPersonalityRandomReqDto {

    private Integer memberLevel;
    private List<Long> personalityIds;
}
