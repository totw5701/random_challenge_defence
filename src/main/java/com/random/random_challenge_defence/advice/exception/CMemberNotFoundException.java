package com.random.random_challenge_defence.advice.exception;

public class CMemberNotFoundException extends RuntimeException{

    public CMemberNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CMemberNotFoundException(String msg) {
        super(msg);
    }

    public CMemberNotFoundException() {
        super();
    }
}
