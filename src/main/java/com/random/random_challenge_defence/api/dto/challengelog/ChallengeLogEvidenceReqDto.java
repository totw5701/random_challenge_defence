package com.random.random_challenge_defence.api.dto.challengelog;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeLogEvidenceReqDto {

    @ApiModelProperty(example = "long, 챌린지 이력 id")
    private Long challengeLogId;
    @ApiModelProperty(example = "arr, file id 배열")
    private List<Long> evidenceIdList;
}
