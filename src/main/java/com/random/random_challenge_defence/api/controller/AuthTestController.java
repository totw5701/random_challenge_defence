package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.config.auth.JwtTokenProvider;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthTestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    private final ResponseService responseService;

    @GetMapping("/config-test")
    public String configTest() {

        return "config test";
    }


    @GetMapping("/permit-all")
    public String permitAll() {
        return "permit all";
    }

    @GetMapping("/user-only")
    public String userOnly() {
        return "user-only";
    }

    @GetMapping("/admin-only")
    public String adminOnly() {
        return "admin-only";
    }


    @GetMapping("/redirect-url")
    public CommonResponse redirectUrl(@RequestParam String code){
        System.out.println("redirect-url");
        System.out.println("code: " + code);
        return null;
    }

    @GetMapping("/")
    public String home() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "home";
    }


}
