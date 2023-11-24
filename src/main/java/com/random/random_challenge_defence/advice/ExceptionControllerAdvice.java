package com.random.random_challenge_defence.advice;

import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.advice.exception.StackTraceCustomException;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionControllerAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    public CommonResponse exception(Exception e) {
        log.error("알 수 없는 에러가 발생하였습니다. error: {}", ExceptionUtils.getStackTrace(e));
        return responseService.getFailResult("999", "알 수 없는 서버 에러가 발생하였습니다.");
    }

    @ExceptionHandler(StackTraceCustomException.class)
    public CommonResponse stackTraceCustomException(StackTraceCustomException e) {
        log.error("code={}, errMsg={}, e={}", e.getCode(), e.getMessage(), e.getException().getStackTrace());
        return responseService.getFailResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public CommonResponse customException(CustomException e) {
        // CustomException은 일부러 발생시킨 에러로 stackTrace 로그를 찍지 않는다.
        log.error("code={}, errMsg={}", e.getCode(), e.getMessage());
        return responseService.getFailResult(e.getCode(), e.getMessage());
    }
}
