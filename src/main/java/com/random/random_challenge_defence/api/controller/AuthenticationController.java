package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.api.dto.TokenInfo;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.config.auth.oauth2.OAuthAttributes;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.api.service.AuthenticationService;
import com.random.random_challenge_defence.api.service.MemberService;
import com.random.random_challenge_defence.api.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    private final ResponseService responseService;

    @ApiOperation(value = "토큰 발급", notes = "인가 코드를 통해 토큰을 발급 받습니다.")
    @PostMapping("/token")
    public CommonResponse authToken(
            @ApiParam(value = "ex: {\"social\":\"naver\",\"code\":\"12345\"}", required = true)
            @RequestBody Map<String, String> form) {
        OAuthAttributes oAuthAttributes = authenticationService.getOAuthAttribute(form.get("social"), form.get("code"));
        Member member = memberService.saveOrUpdate(oAuthAttributes);
        TokenInfo tokenInfo = authenticationService.generateTokenInfo(member);
        return responseService.getResult(tokenInfo);
    }


    @ApiOperation(value = "토큰 재발급", notes = "만료되지 않은 토큰으로 사용가능한 토큰을 발급 받습니다.")
    @GetMapping("/token-reissue")
    public CommonResponse<TokenInfo> tokenReissue(
            @ApiParam(value = "Header: Authorization=RTK", required = true) HttpServletRequest request) {
        String email = authenticationService.resolveEmail(request);
        Member member = memberService.getEntityById(email);
        TokenInfo tokenInfo = authenticationService.generateTokenInfo(member);
        return responseService.getResult(tokenInfo);
    }

    @GetMapping("/login-fail")
    public void loginFail(HttpServletRequest request) {
        ExceptionCode ex = (ExceptionCode) request.getAttribute("token-exception");
        if (ExceptionCode.TOKEN_IS_NULL == ex) {
            throw new CustomException(ExceptionCode.TOKEN_IS_NULL);
        } else if (ExceptionCode.TOKEN_VALIDATION_FAIL == ex) {
            throw new CustomException(ExceptionCode.TOKEN_VALIDATION_FAIL);
        } else {
            throw new CustomException(ExceptionCode.SOCIAL_LOGIN_FAIL);
        }
    }

    @GetMapping("/access-denied")
    public void accessDenied() {
        throw new CustomException(ExceptionCode.ACCESS_DENIED);
    }

}
