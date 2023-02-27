package com.random.random_challenge_defence.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    TOKEN_IS_NULL("", "token is empty"),
    TOKEN_VALIDATION_FAIL("", "token validation fail"),
    TOKEN_EXPIRED("", "token expired");

    private final String code;
    private final String description;
}
