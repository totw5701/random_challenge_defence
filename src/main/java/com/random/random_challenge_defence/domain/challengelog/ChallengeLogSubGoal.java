package com.random.random_challenge_defence.domain.challengelog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoal;
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
    private ChallengeSubGoal challengeSubGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeLog challengeLog;

    @Enumerated(EnumType.STRING)
    private ChallengeLogStatus memberChallengeStatus;
}
