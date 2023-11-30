package com.random.random_challenge_defence.domain.challengecard;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengePutReqDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardmemberpersonality.ChallengeCardMemberPersonality;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.file.File;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Challenge 객체를 JSON으로 바꿀때 다른 객체에게 참조 받는 경우 ID 만 넘긴다.
public class ChallengeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeCardCategory challengeCardCategory;

    @Column(unique = true)
    private String title;
    private String description;
    private String finalGoal;
    private Integer difficulty;
    private Integer assignScore;
    private Integer experience;

    private String createDtm;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_card_id")
    private List<ChallengeCardSubGoal> challengeCardSubGoals;

    @OneToMany(mappedBy = "challengeCard")
    private List<ChallengeCardMemberPersonality> challengeCardMemberPersonalities;

    public ChallengeDetailDto toDetailDto() {
        List<ChallengeSubGoalDetailDto> subGoals = new ArrayList<>();
        if(this.challengeCardSubGoals != null) {
            subGoals = challengeCardSubGoals.stream().map((challengeSubGoal -> challengeSubGoal.toDto())).collect(Collectors.toList());
        }
        return ChallengeDetailDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .finalGoal(this.finalGoal)
                .difficulty(this.difficulty)
                .assignScore(this.assignScore)
                .createDtm(this.createDtm)
                .experience(this.experience)
                .challengeSubGoals(subGoals)
                .challengeCardCategory(this.challengeCardCategory.toDetailDto())
                .build();
    }
}
