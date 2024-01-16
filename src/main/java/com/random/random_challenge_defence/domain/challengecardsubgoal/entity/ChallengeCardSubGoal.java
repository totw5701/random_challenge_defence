package com.random.random_challenge_defence.domain.challengecardsubgoal.entity;

import com.random.random_challenge_defence.domain.challengecardsubgoal.dto.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecard.entity.ChallengeCard;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_card_id")
    private ChallengeCard challengeCard;

    private String subGoal;

    public ChallengeSubGoalDetailDto toDto() {
        return ChallengeSubGoalDetailDto.builder()
                .id(this.id)
                .subGoal(this.subGoal)
                .build();
    }

}
