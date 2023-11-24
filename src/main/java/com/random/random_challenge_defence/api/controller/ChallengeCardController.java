package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeCardDeleteReqDto;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengePutReqDto;
import com.random.random_challenge_defence.api.service.ChallengeCardService;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.api.service.S3FileUploadService;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/challenge-card")
@RequiredArgsConstructor
public class ChallengeCardController {

    private final ChallengeCardService challengeCardService;
    private final S3FileUploadService s3FileUploadService;
    private final ResponseService responseService;

    @ApiOperation(value = "챌린지 카드 리스트 페이징 조회", notes = "챌린지 리스트를 15건씩 페이징하여 조회합니다.")
    @GetMapping("/list")
    public CommonResponse<Page<ChallengeDetailDto>> list(@RequestParam(name = "nowPage", defaultValue = "0") Integer nowPage){
        Page<ChallengeDetailDto> challengeDetailDtos = challengeCardService.readPageList(nowPage);
        return responseService.getResult(challengeDetailDtos);
    }

    @ApiOperation(value = "챌린지 카드 조회", notes = "단건 챌린지를 조회합니다.")
    @GetMapping("/{id}")
    public CommonResponse<ChallengeDetailDto> detail(@PathVariable Long id, Model model){
        ChallengeDetailDto challenge = challengeCardService.readOne(id);
        return responseService.getResult(challenge);
    }

}
