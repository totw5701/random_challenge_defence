package com.random.random_challenge_defence.advice.exception;

import com.random.random_challenge_defence.advice.ExceptionCode;
import lombok.Data;

@Data
public class CustomException extends RuntimeException{
    private String code;

    public CustomException(String code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }

    public CustomException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDescription());
        this.code = exceptionCode.getCode();
    }

    public CustomException() {
        super();
    }
}
