package com.random.random_challenge_defence.global.advice.exception;

import com.random.random_challenge_defence.global.advice.ExceptionCode;
import lombok.Data;

@Data
public class StackTraceCustomException extends RuntimeException{
    private Exception exception;
    private String code;

    public StackTraceCustomException(String code, String msg, Exception exception, Throwable t) {
        super(msg, t);
        this.exception = exception;
        this.code = code;
    }

    public StackTraceCustomException(String code, String msg, Exception exception) {
        super(msg);
        this.exception = exception;
        this.code = code;
    }

    public StackTraceCustomException(ExceptionCode exceptionCode, Exception exception) {
        super(exceptionCode.getDescription());
        this.code = exceptionCode.getCode();
        this.exception = exception;
    }

    public StackTraceCustomException() {
        super();
    }
}
