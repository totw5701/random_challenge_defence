package com.random.random_challenge_defence.domain.challenge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeSubGoalDetailDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ChallengeSubGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_sub_goal_seq")
    //@SequenceGenerator(name = "challenge_sub_goal_seq", sequenceName = "challenge_sub_goal_seq", initialValue = 10)
    private Long id;

    @ManyToOne
    private Challenge challenge;
    private String intermediateGoal;


    public ChallengeSubGoalDetailDto toDto() {
        return ChallengeSubGoalDetailDto.builder()
                .id(this.id)
                .intermediateGoal(this.intermediateGoal)
                .build();
    }

}
