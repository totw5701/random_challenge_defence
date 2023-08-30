package com.random.random_challenge_defence.domain.challengecard;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengePutReqDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
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
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_seq")
    //@SequenceGenerator(name = "challenge_seq", sequenceName = "challenge_seq", initialValue = 500)
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

    @OneToOne(mappedBy = "challengeCard")
    @JoinColumn(name = "challenge_card_id")
    private File image;

    private String createDtm;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_card_id")
    private List<ChallengeCardSubGoal> challengeCardSubGoals;

    public void update(ChallengePutReqDto form) {
        this.title = (form.getTitle() != null) ? form.getTitle() : this.title;
        this.description = (form.getDescription() != null) ? form.getDescription() : this.description;
        this.finalGoal = (form.getFinalGoal() != null) ? form.getFinalGoal() : this.finalGoal;
        this.difficulty = (form.getDifficulty() != null) ? form.getDifficulty() : this.difficulty;
        this.assignScore = (form.getAssignScore() != null) ? form.getAssignScore() : this.assignScore;
        this.experience = (form.getExperience() != null) ? form.getExperience() : this.experience;

    }

    public void imageUpdate(File newImage) {
        this.image = newImage;
    }

    public void updateChallengeCardCategory(ChallengeCardCategory challengeCardCategory) {
        this.challengeCardCategory = challengeCardCategory;
    }

    public ChallengeDetailDto toDetailDto() {
        List<ChallengeSubGoalDetailDto> subGoals = new ArrayList<>();
        if(this.challengeCardSubGoals != null) {
            subGoals = challengeCardSubGoals.stream().map((challengeSubGoal -> challengeSubGoal.toDto())).collect(Collectors.toList());
        }
        if(this.image == null) {
            this.image = new File();
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
                .image(this.image.toDto())
                .build();
    }

    public void assignSubGoals(List<ChallengeCardSubGoal> subGoals){
        this.challengeCardSubGoals = subGoals;
    }
}
