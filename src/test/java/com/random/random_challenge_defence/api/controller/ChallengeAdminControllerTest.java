package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.service.ChallengeService;
import com.random.random_challenge_defence.service.ResponseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


@ExtendWith(MockitoExtension.class)
class ChallengeAdminControllerTest {

    @Mock
    private ChallengeService challengeService;

    @Spy
    private ResponseService responseService;

    @InjectMocks
    private ChallengeController controller;

    @Test
    @DisplayName("Challenge 목록 조회")
    void list() throws Exception {
        // given
        Page<ChallengeDetailDto> challengeDetailDtos = new PageImpl<>(Arrays.asList(
                ChallengeDetailDto.builder()
                        .id(1L)
                        .title("title1")
                        .description("desc1")
                        .finalGoal("100")
                        .build(),
                ChallengeDetailDto.builder()
                        .id(1L)
                        .title("title2")
                        .description("desc2")
                        .finalGoal("200")
                        .build()
        ));
        given(challengeService.readPageList(0)).willReturn(challengeDetailDtos);

        CommonResponse<Page<ChallengeDetailDto>> expectedResponse = responseService.getResult(challengeDetailDtos);

        // when
        CommonResponse<Page<ChallengeDetailDto>> actualResponse = controller.list(0);

        // then
        verify(challengeService).readPageList(0);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Challenge 상세 조회")
    void detail() throws Exception {
        // given
        Long id = 1L;
        ChallengeDetailDto challengeDetailDto = ChallengeDetailDto.builder()
                .id(1L)
                .title("title1")
                .description("desc1")
                .finalGoal("100")
                .build();
        given(challengeService.readOne(id)).willReturn(challengeDetailDto);

        CommonResponse<ChallengeDetailDto> expectedResponse = responseService.getResult(challengeDetailDto);

        // when
        CommonResponse<ChallengeDetailDto> actualResponse = controller.detail(id, null);

        // then
        verify(challengeService).readOne(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Challenge 생성")
    void create() throws Exception {
        // given
        ChallengePutReqDto form = ChallengePutReqDto.builder()
                .difficulty(2)
                .description("desc")
                .evidenceType("P")
                .finalGoal("fianl")
                .challengeSubGoals(Arrays.asList("sub1", "sub2"))
                .assignScore(2)
                .build();

        Challenge challenge = Challenge.builder()
                .assignScore(form.getAssignScore())
                .title(form.getTitle())
                .description(form.getDescription())
                .evidenceType(form.getEvidenceType())
                .finalGoal(form.getFinalGoal())
                .createDate(any())
                .build();
        given(challengeService.create(form)).willReturn(challenge);

        CommonResponse expectedResponse = responseService.getSuccessResult();

        // when
        CommonResponse actualResponse = controller.put(form);

        // then
        verify(challengeService).create(form);

        assertEquals(expectedResponse, actualResponse);
    }
}