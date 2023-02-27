package com.random.random_challenge_defence.advice.exception;

public class CAuthenticationException extends RuntimeException{

    public CAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAuthenticationException(String msg) {
        super(msg);
    }

    public CAuthenticationException() {
        super();
    }
}
