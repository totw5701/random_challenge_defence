package com.random.random_challenge_defence.domain.challengeCard;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
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
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_seq")
    //@SequenceGenerator(name = "challenge_seq", sequenceName = "challenge_seq", initialValue = 500)
    private Long id;

    @Column(unique = true)
    private String title;
    private String description;
    private String finalGoal;
    private String evidenceType;
    private Integer difficulty;
    private Integer assignScore;

    private Long createDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_id")
    private List<ChallengeCardSubGoal> challengeCardSubGoals;

    public void update(ChallengePutReqDto form) {
        this.title = (form.getTitle() != null) ? form.getTitle() : this.title;
        this.description = (form.getDescription() != null) ? form.getDescription() : this.description;
        this.finalGoal = (form.getFinalGoal() != null) ? form.getFinalGoal() : this.finalGoal;
        this.evidenceType = (form.getEvidenceType() != null) ? form.getEvidenceType() : this.evidenceType;
        this.difficulty = (form.getDifficulty() != null) ? form.getDifficulty() : this.difficulty;
        this.assignScore = (form.getAssignScore() != null) ? form.getAssignScore() : this.assignScore;

        this.challengeCardSubGoals = (form.getChallengeSubGoals() != null) ?
                    form.getChallengeSubGoals().stream()
                    .map(subGoalDto -> ChallengeCardSubGoal.builder()
                            .challenge(this)
                            .intermediateGoal(subGoalDto)
                            .build())
                    .collect(Collectors.toList())
                : this.challengeCardSubGoals;

    }

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
                .evidenceType(this.evidenceType)
                .difficulty(this.difficulty)
                .assignScore(this.assignScore)
                .createDate(this.createDate)
                .challengeSubGoals(subGoals)
                .build();
    }

    public void assignSubGoals(List<ChallengeCardSubGoal> subGoals){
        this.challengeCardSubGoals = subGoals;
    }
}
