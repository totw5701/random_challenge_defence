package com.random.random_challenge_defence.domain.challengelogsubgoal;

import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
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
    private ChallengeLogStatus memberChallengeStatus;
}