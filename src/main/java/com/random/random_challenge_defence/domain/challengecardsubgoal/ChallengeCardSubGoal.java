package com.random.random_challenge_defence.domain.challengecardsubgoal;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengeCard.ChallengeCard;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChallengeCardSubGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_sub_goal_seq")
    //@SequenceGenerator(name = "challenge_sub_goal_seq", sequenceName = "challenge_sub_goal_seq", initialValue = 10)
    private Long id;

    @ManyToOne
    private ChallengeCard challenge;
    private String intermediateGoal;

    public ChallengeSubGoalDetailDto toDto() {
        return ChallengeSubGoalDetailDto.builder()
                .id(this.id)
                .intermediateGoal(this.intermediateGoal)
                .build();
    }

}
