package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.service.ChallengeService;
import com.random.random_challenge_defence.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * 프론트 단에서 페이지 지원받고 얘도 api로 개발 할 것.
 */
@RestControllerAdvice
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
    private final ResponseService responseService;

    @GetMapping("/challenges")
    public CommonResponse<Page<ChallengeDetailDto>> list(@RequestParam(name = "nowPage", defaultValue = "0") Integer nowPage){
        Page<ChallengeDetailDto> challengeDetailDtos = challengeService.readPageList(nowPage);
        return responseService.getResult(challengeDetailDtos);
    }

    @GetMapping("/challenges/{id}")
    public CommonResponse<ChallengeDetailDto> detail(@PathVariable Long id, Model model){
        ChallengeDetailDto challenge = challengeService.readOne(id);
        return responseService.getResult(challenge);
    }


    @PostMapping("/challenges")
    public CommonResponse put(@RequestBody ChallengePutReqDto form) {
        if(form.getId() != null){               // 수정하는 경우
            challengeService.update(form);
        } else {                                // 생성하는 경우
            challengeService.create(form);
        }
        return responseService.getSuccessResult();
    }

}
