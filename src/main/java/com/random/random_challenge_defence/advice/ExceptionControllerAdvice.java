package com.random.random_challenge_defence.advice;

import com.random.random_challenge_defence.advice.exception.*;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    /**
     *  200 : 정상
     *  400대: 클라이언트 에러
     *  500대: 서버 에러
     */

    private final ResponseService responseService;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public CommonResponse exceptionHandler(Exception e) {
        System.out.println(e.toString());
        return responseService.getFailResult("500", "알 수 없는 내부 에러가 발생했습니다.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public CommonResponse memberNotFoundException(CMemberNotFoundException e) {
        System.out.println(e);
        return responseService.getFailResult("400", "회원 정보가 없습니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public CommonResponse AuthenticationException(CAuthenticationException e) {
        System.out.println(e);
        return responseService.getFailResult("402", "로그인에 실패하였습니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public CommonResponse accessDeniedException(CAccessDeniedException e) {
        System.out.println(e);
        return responseService.getFailResult("403", "접근이 거부되었습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResponse bindExHandler(BindException e) {
        System.out.println(e);
        FieldError err = e.getFieldError();
        return responseService.getFailResult("404", String.valueOf( String.valueOf(err.getField()) + " " + err.getDefaultMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResponse reqParamExHandler(MissingServletRequestParameterException e) {
        System.out.println(e);
        return responseService.getFailResult("405", "파라미터 값이 없습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResponse tokenNotFoundException(CTokenNotFoundException e) {
        System.out.println(e);
        return responseService.getFailResult("406", "토큰 정보가 없습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResponse tokenValidationFailException(CTokenValiationFailException e) {
        System.out.println(e);
        return responseService.getFailResult("407", "사용할 수 없는 토큰입니다.");
    }
}
