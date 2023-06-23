package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.ChallengeCardService;
import com.random.random_challenge_defence.api.service.ResponseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


@ExtendWith(MockitoExtension.class)
class ChallengeAdminControllerTest {

    @Mock
    private ChallengeCardService challengeCardService;

    @Spy
    private ResponseService responseService;

    @InjectMocks
    private ChallengeCardController controller;

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
        given(challengeCardService.readPageList(0)).willReturn(challengeDetailDtos);

        CommonResponse<Page<ChallengeDetailDto>> expectedResponse = responseService.getResult(challengeDetailDtos);

        // when
        CommonResponse<Page<ChallengeDetailDto>> actualResponse = controller.list(0);

        // then
        verify(challengeCardService).readPageList(0);

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
        given(challengeCardService.readOne(id)).willReturn(challengeDetailDto);

        CommonResponse<ChallengeDetailDto> expectedResponse = responseService.getResult(challengeDetailDto);

        // when
        CommonResponse<ChallengeDetailDto> actualResponse = controller.detail(id, null);

        // then
        verify(challengeCardService).readOne(id);

        assertEquals(expectedResponse, actualResponse);
    }


}