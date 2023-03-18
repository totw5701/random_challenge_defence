package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CAccessDeniedException;
import com.random.random_challenge_defence.advice.exception.CAuthenticationException;
import com.random.random_challenge_defence.advice.exception.CTokenNotFoundException;
import com.random.random_challenge_defence.advice.exception.CTokenValiationFailException;
import com.random.random_challenge_defence.api.dto.TokenInfo;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.member.MemberLoginReqDto;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.service.AuthenticationService;
import com.random.random_challenge_defence.service.MemberService;
import com.random.random_challenge_defence.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final MemberService memberService;
    private final ResponseService responseService;


    @ApiOperation(value = "토큰 재발급", notes = "만료되지 않은 토큰으로 사용가능한 토큰을 발급받습니다.")
    @GetMapping("/token-reissue")
    public CommonResponse<TokenInfo> tokenReissue(HttpServletRequest request) {
        String token = authenticationService.resolveToken(request);
        Member member = memberService.findByEmail(authenticationService.resolveSubject(token));
        TokenInfo tokenInfo = authenticationService.login(member.getEmail(), member.getPassword());
        return responseService.getResult(tokenInfo);
    }

    @ApiOperation(value = "로그인", notes = "로그인 하여 토큰을 발급합니다.")
    @PostMapping("/login")
    public CommonResponse<TokenInfo> login(@Valid @RequestBody MemberLoginReqDto form) {
        TokenInfo tokenInfo = authenticationService.login(form.getEmail(), form.getPassword());
        return responseService.getResult(tokenInfo);
    }

    /**
     * 토큰이 없거나 혹은 토큰이 만료되었음.
     */
    @GetMapping("/login-fail")
    public void loginFail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExceptionCode ex = (ExceptionCode) request.getAttribute("token-exception");
        if (ExceptionCode.TOKEN_IS_NULL == ex) {
            throw new CTokenNotFoundException();
        } else if (ExceptionCode.TOKEN_VALIDATION_FAIL == ex) {
            throw new CTokenValiationFailException();
        } else {
            throw new CAuthenticationException();
        }
    }

    @GetMapping("/access-denied")
    public void accessDenied() {
        throw new CAccessDeniedException();
    }

    // TEST 코드
    @GetMapping("all")
    public CommonResponse<Object> all() {
        return responseService.getStringResult("permitAll", "permitAll");
    }

    @GetMapping("user")
    public CommonResponse<Object> user() {
        return responseService.getStringResult("userOnly", "userOnly");
    }

    @GetMapping("user/suc")
    public CommonResponse sdfdf() {
        return responseService.getSuccessResult();
    }
}
