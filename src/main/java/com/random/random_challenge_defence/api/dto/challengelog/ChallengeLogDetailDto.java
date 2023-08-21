package com.random.random_challenge_defence.api.dto.challengelog;

import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.file.FileDetailDto;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;


@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeLogDetailDto {

    @ApiModelProperty(example = "long, 챌린지 이력 id")
    private Long id;
    @ApiModelProperty(example = "챌린지 카드 id")
    private Long challengeCardId;
    @ApiModelProperty(example = "챌린지 상태, READY, SUCCESS, PASS, ACTION")
    private ChallengeLogStatus status;
    @ApiModelProperty(example = "사용자가 입력한 도전 후기")
    private String review;
    @ApiModelProperty(example = "이력 소유 회원 id")
    private Long memberId;
    private List<ChallengeLogSubGoalDetailDto> challengeLogSubGoalDetailDtos;
    private List<FileDetailDto> fileDetailDto;

}
