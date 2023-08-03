package com.random.random_challenge_defence.domain.challengelogsubgoal;

import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChallengeLogSubGoal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeCardSubGoal challengeCardSubGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeLog challengeLog;

    @Enumerated(EnumType.STRING)
    private ChallengeLogSubGoalStatus challengeLogSubGoalStatus;


    public ChallengeLogSubGoalDetailDto toDetail() {
        return ChallengeLogSubGoalDetailDto.builder()
                .id(id)
                .subGoal(challengeCardSubGoal.getSubGoal())
                .status(challengeLogSubGoalStatus.getCode())
                .build();
    }

    public void statusChange(ChallengeLogSubGoalStatus status) {
        this.challengeLogSubGoalStatus = status;
    }
}
