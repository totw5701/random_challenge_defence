package com.random.random_challenge_defence.api.dto.challenge;

import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Builder
@Getter
public class ChallengeLogUpdateDto {

    private Long id;
    private String evidence;
    private ChallengeLogStatus status;
    private String review;
}
