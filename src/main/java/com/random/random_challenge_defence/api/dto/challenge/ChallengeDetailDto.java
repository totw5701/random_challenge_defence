package com.random.random_challenge_defence.api.dto.challenge;

import com.random.random_challenge_defence.api.dto.file.S3DetailFileDto;
import com.random.random_challenge_defence.api.dto.file.S3UploadFileDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.file.S3File;
import lombok.Builder;
import lombok.Data;

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
    private String createDtm;
    private List<ChallengeSubGoalDetailDto> challengeSubGoals;
    private ChallengeCardCategory challengeCardCategory;
    private S3DetailFileDto image;
}
