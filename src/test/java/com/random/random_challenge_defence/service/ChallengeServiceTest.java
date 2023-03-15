package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeSubGoalDetailDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.challenge.ChallengeRepository;
import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoal;
import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {
    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private ChallengeSubGoalRepository challengeSubGoalRepository;

    @InjectMocks
    private ChallengeService challengeService;

    @Test
    @DisplayName("readPageList() 메서드 테스트")
    void testReadPageList() {
        // given
        int nowPage = 0;
        List<Challenge> challengeList = new ArrayList<>();
        challengeList.add(
                Challenge.builder()
                        .id(1L)
                        .assignScore(3)
                        .title("titlt1")
                        .description("desc1")
                        .evidenceType("P")
                        .finalGoal("final1")
                        .createDate(13333L)
                        .build()
        );
        challengeList.add(
                Challenge.builder()
                        .id(1L)
                        .assignScore(3)
                        .title("titlt2")
                        .description("desc2")
                        .evidenceType("P")
                        .finalGoal("final2")
                        .createDate(1333333L)
                        .build()
        );
        Page<Challenge> challenges = new PageImpl<>(challengeList);
        when(challengeRepository.findAll(any(Pageable.class))).thenReturn(challenges);

        // when
        Page<ChallengeDetailDto> challengeDetailDtos = challengeService.readPageList(nowPage);

        // then
        assertEquals(challenges.getTotalElements(), challengeDetailDtos.getTotalElements());
        assertEquals(challenges.getContent().get(0).getTitle(), challengeDetailDtos.getContent().get(0).getTitle());
        assertEquals(challenges.getContent().get(0).getDescription(), challengeDetailDtos.getContent().get(0).getDescription());
        assertEquals(challenges.getContent().get(0).getAssignScore(), challengeDetailDtos.getContent().get(0).getAssignScore());
        assertEquals(challenges.getContent().get(1).getTitle(), challengeDetailDtos.getContent().get(1).getTitle());
        assertEquals(challenges.getContent().get(1).getDescription(), challengeDetailDtos.getContent().get(1).getDescription());
        assertEquals(challenges.getContent().get(1).getAssignScore(), challengeDetailDtos.getContent().get(1).getAssignScore());
    }

    @Test
    @DisplayName("create() 메서드")
    void testCreateSuccess() {
        // given
        ChallengePutReqDto form = new ChallengePutReqDto();
        form.setTitle("title1");
        form.setDescription("desc1");
        form.setAssignScore(3);
        form.setEvidenceType("T");
        form.setFinalGoal("final2");
        form.setChallengeSubGoals(Arrays.asList("subgoal1"));

        Challenge challengeEntity = Challenge.builder()
                .id(1L)
                .assignScore(3)
                .title("title1")
                .description("desc1")
                .evidenceType("P")
                .finalGoal("final2")
                .createDate(1333333L)
                .evidenceType("T")
                .build();

        List<ChallengeSubGoal> subGoalEntitys = new ArrayList<>();
        subGoalEntitys.add(ChallengeSubGoal.builder()
                .intermediateGoal("subgoal1")
                .challenge(challengeEntity).build());
        challengeEntity.assignSubGoals(subGoalEntitys);

        when(challengeRepository.save(any(Challenge.class))).thenReturn(challengeEntity);

        // when
        Challenge challenge = challengeService.create(form);

        // then
        assertNotNull(challenge);
        assertNotNull(challenge.getId());
        assertEquals(form.getTitle(), challenge.getTitle());
        assertEquals(form.getDescription(), challenge.getDescription());
        assertEquals(form.getAssignScore(), challenge.getAssignScore());
        assertEquals(form.getEvidenceType(), challenge.getEvidenceType());
        assertEquals(form.getFinalGoal(), challenge.getFinalGoal());
        assertEquals(form.getChallengeSubGoals().size(), challenge.getChallengeSubGoals().size());
        assertEquals(form.getChallengeSubGoals().get(0), challenge.getChallengeSubGoals().get(0).getIntermediateGoal());
        verify(challengeRepository, times(1)).save(any(Challenge.class));

    }

    @Test
    @DisplayName("Should return challenge details when valid challenge ID is provided")
    public void testReadOneValidChallengeId() {
        // Arrange
        Long challengeId = 1L;
        Challenge testChallenge = Challenge.builder()
                .id(challengeId)
                .title("Test Challenge")
                .description("Test Description")
                .finalGoal("Test Final Goal")
                .evidenceType("Test Evidence Type")
                .difficulty(3)
                .assignScore(100)
                .build();
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(testChallenge));
        ChallengeDetailDto expected = testChallenge.toDto();

        // Act
        ChallengeDetailDto actual = challengeService.readOne(challengeId);

        // Assert
        assertEquals(expected, actual);
        verify(challengeRepository, times(1)).findById(challengeId);
    }

    @Test
    @DisplayName("Should throw exception when invalid challenge ID is provided")
    public void testReadOneInvalidChallengeId() {
        // Arrange
        Long challengeId = 1L;
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CChallengeNotFoundException.class, () -> {
            challengeService.readOne(challengeId);
        });
        verify(challengeRepository, times(1)).findById(challengeId);
    }

    @Test
    void testUpdate() {
        // Given
        Long id = 1L;
        String title = "Title";
        String description = "Description";
        String finalGoal = "Final Goal";
        String evidenceType = "Evidence Type";
        Integer difficulty = 5;
        Integer assignScore = 10;

        ChallengePutReqDto form = ChallengePutReqDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .finalGoal(finalGoal)
                .evidenceType(evidenceType)
                .difficulty(difficulty)
                .assignScore(assignScore)
                .build();

        ChallengeSubGoal subGoal = ChallengeSubGoal.builder()
                .id(1L)
                .intermediateGoal("Intermediate Goal")
                .build();

        List<ChallengeSubGoal> subGoals = new ArrayList<>();
        subGoals.add(subGoal);

        Challenge challenge = Challenge.builder()
                .id(id)
                .title("Old Title")
                .description("Old Description")
                .finalGoal("Old Final Goal")
                .evidenceType("Old Evidence Type")
                .difficulty(1)
                .assignScore(2)
                .createDate(0L)
                .challengeSubGoals(subGoals)
                .build();

        Challenge updatedChallenge = Challenge.builder()
                .id(id)
                .title(title)
                .description(description)
                .finalGoal(finalGoal)
                .evidenceType(evidenceType)
                .difficulty(difficulty)
                .assignScore(assignScore)
                .createDate(0L)
                .challengeSubGoals(subGoals)
                .build();

        when(challengeRepository.findById(id)).thenReturn(Optional.of(challenge));

        // When
        Challenge result = challengeService.update(form);

        // Then
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(finalGoal, result.getFinalGoal());
        assertEquals(evidenceType, result.getEvidenceType());
        assertEquals(difficulty, result.getDifficulty());
        assertEquals(assignScore, result.getAssignScore());
        assertEquals(subGoals, result.getChallengeSubGoals());
    }

    @Test
    void testUpdateWhenChallengeNotFound() {
        // Given
        Long id = 1L;

        ChallengePutReqDto form = ChallengePutReqDto.builder()
                .id(id)
                .build();

        when(challengeRepository.findById(id)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(CChallengeNotFoundException.class, () -> challengeService.update(form));
    }


}