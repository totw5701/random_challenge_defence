package com.random.random_challenge_defence.global.result;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService {

    public <T> CommonResponse<T> getStringResult(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return (CommonResponse<T>) CommonResponse.builder()
                .code("0")
                .msg("성공했습니다.")
                .data(map)
                .success(true)
                .build();
    }
    public <T> CommonResponse<T> getResult(T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .code("0")
                .msg("성공했습니다.")
                .data(data)
                .success(true)
                .build();
    }

    public CommonResponse getSuccessResult() {
        return (CommonResponse) CommonResponse.builder()
                .code("0")
                .msg("성공했습니다.")
                .success(true)
                .build();
    }

    public CommonResponse getFailResult(String code, String msg) {
        return (CommonResponse) CommonResponse.builder()
                .code(code)
                .msg(msg)
                .success(false)
                .build();
    }
}
