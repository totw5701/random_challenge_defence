package com.random.random_challenge_defence.api.dto.challenge;

import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoal;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
public class ChallengeDetailDto {

    private Long id;
    private String title;
    private String description;
    private String finalGoal;
    private String evidenceType;
    private Integer difficulty;
    private Integer assignScore;
    private Long createDate;
    private List<ChallengeSubGoalDetailDto> challengeSubGoals;
}
