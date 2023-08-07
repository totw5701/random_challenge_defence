package com.random.random_challenge_defence.advice.exception;

public class CChallengeLogTringFailureException extends RuntimeException{
    public CChallengeLogTringFailureException(String msg, Throwable t) {
        super(msg, t);
    }

    public CChallengeLogTringFailureException(String msg) {
        super(msg);
    }

    public CChallengeLogTringFailureException() {super();}
}
