package com.random.random_challenge_defence.api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private MemberService memberService;

    private String testEmail = "test_user@email.com";

    @BeforeEach
    void setup() {

        MockitoAnnotations.initMocks(this);

        // Security 기본 설정
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(testEmail);
    }

    //@Test
    void getLoginUserEmail() {

        // When
        String loginUserEmail = memberService.getLoginUserEmail();

        // Then
        Assertions.assertThat(loginUserEmail).isEqualTo(testEmail);
    }
}