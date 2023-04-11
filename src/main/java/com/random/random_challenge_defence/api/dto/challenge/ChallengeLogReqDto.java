package com.random.random_challenge_defence.api.dto.challenge;

import lombok.*;

@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeLogReqDto {

    private Long memberId;
    private Long challengeId;
}
