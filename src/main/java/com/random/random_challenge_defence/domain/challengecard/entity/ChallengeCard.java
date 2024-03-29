package com.random.random_challenge_defence.domain.challengecard.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.domain.challengecard.dto.ChallengeDetailDto;
import com.random.random_challenge_defence.domain.challengecardsubgoal.dto.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.memberpersonality.dto.MemberPersonalityDetailDto;
import com.random.random_challenge_defence.domain.challengecardcategory.entity.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardmemberpersonality.entity.ChallengeCardMemberPersonality;
import com.random.random_challenge_defence.domain.challengecardsubgoal.entity.ChallengeCardSubGoal;
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
    @JoinColumn(name = "challenge_card_category_id")
    private ChallengeCardCategory challengeCardCategory;

    @Column(unique = true)
    private String title;
    private String description;
    private String finalGoal;
    private Integer difficulty;
    private Integer assignScore;
    private Integer experience;

    private String createDtm;

    @OneToMany(mappedBy = "challengeCard", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ChallengeCardSubGoal> challengeCardSubGoals;

    @OneToMany(mappedBy = "challengeCard", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ChallengeCardMemberPersonality> challengeCardMemberPersonalities;

    public ChallengeDetailDto toDetailDto() {
        List<ChallengeSubGoalDetailDto> subGoals = new ArrayList<>();
        if(this.challengeCardSubGoals != null) {
            subGoals = challengeCardSubGoals.stream().map((challengeSubGoal -> challengeSubGoal.toDto())).collect(Collectors.toList());
        }
        List<MemberPersonalityDetailDto> personalities = new ArrayList<>();
        if(this.challengeCardMemberPersonalities != null) {
            personalities = this.challengeCardMemberPersonalities.stream().map(entity -> entity.getMemberPersonality().toDetailDto()).collect(Collectors.toList());
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
                .personalities(personalities)
                .build();
    }
}
