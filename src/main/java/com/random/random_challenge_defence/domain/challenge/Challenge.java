package com.random.random_challenge_defence.domain.challenge;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeSubGoalDetailDto;
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
@ToString
public class Challenge {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_seq")
    @SequenceGenerator(name = "challenge_seq", sequenceName = "challenge_seq", initialValue = 500)
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
    private List<ChallengeSubGoal> challengeSubGoals;

    public void update(ChallengePutReqDto form) {
        this.title = (form.getTitle() != null) ? form.getTitle() : this.title;
        this.description = (form.getDescription() != null) ? form.getDescription() : this.description;
        this.finalGoal = (form.getFinalGoal() != null) ? form.getFinalGoal() : this.finalGoal;
        this.evidenceType = (form.getEvidenceType() != null) ? form.getEvidenceType() : this.evidenceType;
        this.difficulty = (form.getDifficulty() != null) ? form.getDifficulty() : this.difficulty;
        this.assignScore = (form.getAssignScore() != null) ? form.getAssignScore() : this.assignScore;

        this.challengeSubGoals = (form.getChallengeSubGoals() != null) ?
                    form.getChallengeSubGoals().stream()
                    .map(subGoalDto -> ChallengeSubGoal.builder()
                            .challenge(this)
                            .intermediateGoal(subGoalDto)
                            .build())
                    .collect(Collectors.toList())
                : this.challengeSubGoals;

    }

    public ChallengeDetailDto toDto() {
        List<ChallengeSubGoalDetailDto> subGoals = new ArrayList<>();
        if(this.challengeSubGoals != null) {
            subGoals = challengeSubGoals.stream().map((challengeSubGoal -> challengeSubGoal.toDto())).collect(Collectors.toList());
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

    public void assignSubGoals(List<ChallengeSubGoal> subGoals){
        this.challengeSubGoals = subGoals;
    }
}
