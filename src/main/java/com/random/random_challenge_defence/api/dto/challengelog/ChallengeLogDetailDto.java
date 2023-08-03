package com.random.random_challenge_defence.api.dto.challengelog;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.file.S3DetailFileDto;
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
    private String evidence;
    private ChallengeLogStatus status;
    private String review;
    private Long memberId;
    private ChallengeDetailDto challengeDetailDto;
    private List<ChallengeLogSubGoalDetailDto> challengeLogSubGoalDetailDtos;
    private S3DetailFileDto image;

}