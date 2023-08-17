package com.random.random_challenge_defence.api.dto.challengelog;

import lombok.*;

import java.util.List;

@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeLogEvidenceReqDto {

    private Long challengeLogId;
    private List<Long> evidenceIdList;
}
