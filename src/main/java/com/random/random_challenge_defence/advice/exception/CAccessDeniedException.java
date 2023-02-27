package com.random.random_challenge_defence.advice.exception;

public class CAccessDeniedException extends RuntimeException{

    public CAccessDeniedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAccessDeniedException(String msg) {
        super(msg);
    }

    public CAccessDeniedException() {
        super();
    }
}
