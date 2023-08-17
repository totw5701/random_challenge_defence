package com.random.random_challenge_defence.domain.challengecardsubgoal;

import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
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
    private ChallengeCard challengeCard;
    private String subGoal;

    public ChallengeSubGoalDetailDto toDto() {
        return ChallengeSubGoalDetailDto.builder()
                .id(this.id)
                .subGoal(this.subGoal)
                .build();
    }

}
