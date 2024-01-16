package com.random.random_challenge_defence.global.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    TOKEN_IS_NULL("1001", "토큰이 비어 있습니다."),
    TOKEN_VALIDATION_FAIL("1002", "토큰 유효성 검사에 실패하였습니다."),
    TOKEN_EXPIRED("1003", "토큰이 만료되었습니다."),
    UNKNOWN_MEMBER("2001", "사용자 정보를 찾을 수 없습니다."),
    INVALID_REQUEST("2002", "잘못된 요청입니다."),
    DATABASE_ERROR("3001", "데이터베이스 오류가 발생하였습니다."),
    EXTERNAL_SERVICE_ERROR("4001", "외부 서비스 오류가 발생하였습니다."),
    ACCESS_DENIED("4002", "권한 없는 요청입니다."),
    SERVICE_USAGE_LIMIT_EXCEEDED("4003", "사용 허용한 최대 갯수를 초과하였습니다."),
    SUCCESS_REQUIREMENTS_NOT_MET("4004", "성공 요구조건을 만족하지 못했습니다."),
    NOT_FOUND_CHALLENGE_CATEGORY("4005", "챌린지 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_FILE("4006", "파일을 찾을 수 없습니다."),
    NOT_FOUND_CHALLENGE_CARD("4007", "챌린지 카드를 찾을 수 없습니다"),
    NOT_FOUND_CHALLENGE_LOG("4008", "챌린지 도전 이력을 찾을 수 없습니다"),
    NOT_FOUND_CHALLENGE_SUB_GOAL("4009", "중간도전을 찾을 수 없습니다"),
    NOT_FOUND_PERSONALITY("4010", "사용자 특성을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER("4011", "사용자를 찾을 수 없습니다."),
    NOT_FOUND_FEEDBACK("4012", "챌린지 카드 평가를 찾을 수 없습니다."),
    UNSUPPORTED_SOCIAL_LOGIN("5001", "제공하지 않는 소셜 로그인 플랫폼 입니다."),
    SOCIAL_LOGIN_ERROR("5002", "소셜 로그인 과정에서 오류가 발생하였습니다."),
    SOCIAL_LOGIN_FAIL("5003", "소셜 로그인에 실패하였습니다."),
    FILE_UPLOAD_FAIL("5004", "파일 업로드에 실패하였습니다"),
    UNKNOWN_EXCEPTION("9999", "알 수 없는 서버 오류가 발생하였습니다.");

    private final String code;
    private final String description;
}
