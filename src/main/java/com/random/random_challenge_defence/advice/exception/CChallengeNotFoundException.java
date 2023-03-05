package com.random.random_challenge_defence.advice.exception;

public class CChallengeNotFoundException extends RuntimeException{

    public CChallengeNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CChallengeNotFoundException(String msg) {
        super(msg);
    }

    public CChallengeNotFoundException() {
        super();
    }
}
