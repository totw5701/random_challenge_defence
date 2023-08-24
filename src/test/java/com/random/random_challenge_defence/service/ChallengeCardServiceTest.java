package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengePutReqDto;
import com.random.random_challenge_defence.api.service.ChallengeCardService;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ChallengeCardServiceTest {
    @Mock
    private ChallengeCardRepository challengeCardRepository;

    @Mock
    private ChallengeCardSubGoalRepository challengeCardSubGoalRepository;

    @InjectMocks
    private ChallengeCardService challengeCardService;

    //@Test
    @DisplayName("readPageList() 메서드 테스트")
    void testReadPageList() {
        // given
        int nowPage = 0;
        List<ChallengeCard> challengeList = new ArrayList<>();
        challengeList.add(
                ChallengeCard.builder()
                        .id(1L)
                        .assignScore(3)
                        .title("titlt1")
                        .description("desc1")
                        .finalGoal("final1")
                        .build()
        );
        challengeList.add(
                ChallengeCard.builder()
                        .id(1L)
                        .assignScore(3)
                        .title("titlt2")
                        .description("desc2")
                        .finalGoal("final2")
                        .build()
        );
        Page<ChallengeCard> challenges = new PageImpl<>(challengeList);
        when(challengeCardRepository.findAll(any(Pageable.class))).thenReturn(challenges);

        // when
        Page<ChallengeDetailDto> challengeDetailDtos = challengeCardService.readPageList(nowPage);

        // then
        assertEquals(challenges.getTotalElements(), challengeDetailDtos.getTotalElements());
        assertEquals(challenges.getContent().get(0).getTitle(), challengeDetailDtos.getContent().get(0).getTitle());
        assertEquals(challenges.getContent().get(0).getDescription(), challengeDetailDtos.getContent().get(0).getDescription());
        assertEquals(challenges.getContent().get(0).getAssignScore(), challengeDetailDtos.getContent().get(0).getAssignScore());
        assertEquals(challenges.getContent().get(1).getTitle(), challengeDetailDtos.getContent().get(1).getTitle());
        assertEquals(challenges.getContent().get(1).getDescription(), challengeDetailDtos.getContent().get(1).getDescription());
        assertEquals(challenges.getContent().get(1).getAssignScore(), challengeDetailDtos.getContent().get(1).getAssignScore());
    }


    //@Test
    @DisplayName("Should return challenge details when valid challenge ID is provided")
    public void testReadOneValidChallengeId() {
        // Arrange
        Long challengeId = 1L;
        ChallengeCard testChallenge = ChallengeCard.builder()
                .id(challengeId)
                .title("Test Challenge")
                .description("Test Description")
                .finalGoal("Test Final Goal")
                .difficulty(3)
                .assignScore(100)
                .build();
        when(challengeCardRepository.findById(challengeId)).thenReturn(Optional.of(testChallenge));
        ChallengeDetailDto expected = testChallenge.toDetailDto();

        // Act
        ChallengeDetailDto actual = challengeCardService.readOne(challengeId);

        // Assert
        assertEquals(expected, actual);
        verify(challengeCardRepository, times(1)).findById(challengeId);
    }

    //@Test
    @DisplayName("Should throw exception when invalid challenge ID is provided")
    public void testReadOneInvalidChallengeId() {
        // Arrange
        Long challengeId = 1L;
        when(challengeCardRepository.findById(challengeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CChallengeNotFoundException.class, () -> {
            challengeCardService.readOne(challengeId);
        });
        verify(challengeCardRepository, times(1)).findById(challengeId);
    }

    //@Test
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

        ChallengeCardSubGoal subGoal = ChallengeCardSubGoal.builder()
                .id(1L)
                .build();

        List<ChallengeCardSubGoal> subGoals = new ArrayList<>();
        subGoals.add(subGoal);

        ChallengeCard challenge = ChallengeCard.builder()
                .id(id)
                .title("Old Title")
                .description("Old Description")
                .finalGoal("Old Final Goal")
                .difficulty(1)
                .assignScore(2)
                .challengeCardSubGoals(subGoals)
                .build();

        ChallengeCard updatedChallenge = ChallengeCard.builder()
                .id(id)
                .title(title)
                .description(description)
                .finalGoal(finalGoal)
                .difficulty(difficulty)
                .assignScore(assignScore)
                .challengeCardSubGoals(subGoals)
                .build();

        when(challengeCardRepository.findById(id)).thenReturn(Optional.of(challenge));

        // When
        ChallengeCard result = challengeCardService.update(form);

        // Then
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(finalGoal, result.getFinalGoal());
        assertEquals(difficulty, result.getDifficulty());
        assertEquals(assignScore, result.getAssignScore());
        assertEquals(subGoals, result.getChallengeCardSubGoals());
    }

    //@Test
    void testUpdateWhenChallengeNotFound() {
        // Given
        Long id = 1L;

        ChallengePutReqDto form = ChallengePutReqDto.builder()
                .id(id)
                .build();

        when(challengeCardRepository.findById(id)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(CChallengeNotFoundException.class, () -> challengeCardService.update(form));
    }


}