package com.random.random_challenge_defence.api.dto.challengelog;

import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.file.FileDetailDto;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import lombok.*;

import java.util.List;


@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeLogDetailDto {

    private Long id;
    private ChallengeLogStatus status;
    private String review;
    private Long memberId;
    private List<ChallengeLogSubGoalDetailDto> challengeLogSubGoalDetailDtos;
    private List<FileDetailDto> fileDetailDto;
    private ChallengeDetailDto challengeCardDetailDto;

}
