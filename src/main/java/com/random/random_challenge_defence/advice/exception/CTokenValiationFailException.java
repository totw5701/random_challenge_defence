package com.random.random_challenge_defence.advice.exception;

public class CTokenValiationFailException extends RuntimeException{
    public CTokenValiationFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public CTokenValiationFailException(String msg) {
        super(msg);
    }

    public CTokenValiationFailException() {
        super();
    }
}
