package com.random.random_challenge_defence.domain.challengecardfeedback.controller;

import com.random.random_challenge_defence.domain.challengecardfeedback.dto.ChallengeCardFeedbackDetailDto;
import com.random.random_challenge_defence.domain.challengecardfeedback.dto.ChallengeCardFeedbackReqDto;
import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.challengecardfeedback.service.ChallengeCardFeedbackService;
import com.random.random_challenge_defence.global.result.ResponseService;
import com.random.random_challenge_defence.domain.challengecardfeedback.entity.ChallengeCardFeedback;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge-card-feedback")
public class ChallengeCardFeedbackController {

    private final ChallengeCardFeedbackService challengeCardFeedbackService;
    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 카드 평가 작성", notes = "챌린지 카드에 대한 평가를 작성합니다.")
    @PostMapping("/create")
    public CommonResponse<ChallengeCardFeedbackDetailDto> create(@Validated @RequestBody ChallengeCardFeedbackReqDto form){
        ChallengeCardFeedback challengeCardFeedback = challengeCardFeedbackService.create(form);
        return responseService.getResult(challengeCardFeedback.toDetailDto());
    }

    @ApiOperation(value = "챌린지 카드 평가 수정", notes = "챌린지 카드에 대한 평가를 수정합니다.")
    @PostMapping("/update")
    public CommonResponse<ChallengeCardFeedbackDetailDto> update(@Validated @RequestBody ChallengeCardFeedbackReqDto form){
        ChallengeCardFeedback challengeCardFeedback = challengeCardFeedbackService.update(form);
        return responseService.getResult(challengeCardFeedback.toDetailDto());
    }

}
