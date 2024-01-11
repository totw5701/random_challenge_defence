package com.random.random_challenge_defence.api.dto.challengecardfeedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeCardFeedbackDetailDto {

    private Long challengeCardId;
    private int rating;
    private String review;
}
