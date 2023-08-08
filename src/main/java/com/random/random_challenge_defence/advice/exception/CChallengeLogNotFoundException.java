package com.random.random_challenge_defence.advice.exception;

public class CChallengeLogNotFoundException extends RuntimeException{
    public CChallengeLogNotFoundException() {
    }

    public CChallengeLogNotFoundException(String message) {
        super(message);
    }

    public CChallengeLogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
