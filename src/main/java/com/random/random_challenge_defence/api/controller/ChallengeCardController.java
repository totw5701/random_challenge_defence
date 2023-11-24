package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.ChallengeCardService;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/challenge-card")
@RequiredArgsConstructor
public class ChallengeCardController {

    private final ChallengeCardService challengeCardService;
    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 카드 조회", notes = "단건 챌린지를 조회합니다.")
    @GetMapping("/{id}")
    public CommonResponse<ChallengeDetailDto> detail(@PathVariable Long id){
        ChallengeCard challenge = challengeCardService.getEntityById(id);
        return responseService.getResult(challenge.toDetailDto());
    }

}
