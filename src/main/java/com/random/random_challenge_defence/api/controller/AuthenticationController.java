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


    @GetMapping("/token-reissue")
    public CommonResponse<TokenInfo> tokenReissue(HttpServletRequest request) {
        String token = authenticationService.resolveToken(request);
        Member member = memberService.findByEmail(authenticationService.resolveSubject(token));
        TokenInfo tokenInfo = authenticationService.login(member.getEmail(), member.getPassword());
        return responseService.getResult(tokenInfo);
    }

    @PostMapping("/login")
    public CommonResponse<TokenInfo> login(@Valid @RequestBody MemberLoginReqDto form) {
        TokenInfo tokenInfo = authenticationService.login(form.getEmail(), form.getPassword());
        return responseService.getResult(tokenInfo);
    }

    /**
     * ????????? ????????? ?????? ????????? ???????????????.
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

    // TEST ??????
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
