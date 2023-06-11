package com.random.random_challenge_defence.api.dto.challenge;

import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
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
    private Long challengeId;
    private List<ChallengeLogSubGoalDetailDto> challengeLogSubGoalDetailDtos;
}
