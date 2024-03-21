package com.random.random_challenge_defence.domain.challengelogsubgoal.entity;

import com.random.random_challenge_defence.domain.challengelog.dto.ChallengeLogSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecardsubgoal.entity.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelog.entity.ChallengeLog;
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
    @JoinColumn(name = "challenge_card_sub_goal_id")
    private ChallengeCardSubGoal challengeCardSubGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_log_id")
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
