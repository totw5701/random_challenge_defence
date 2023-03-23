package com.random.random_challenge_defence.advice.exception;

public class CNotFoundException extends RuntimeException{

    public CNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CNotFoundException(String msg) {
        super(msg);
    }

    public CNotFoundException() {
        super();
    }
}
