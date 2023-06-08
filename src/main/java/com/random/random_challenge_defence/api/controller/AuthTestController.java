package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.TokenInfo;
import com.random.random_challenge_defence.config.auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthTestController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

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

    @GetMapping("/")
    public String home() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "home";
    }


}
