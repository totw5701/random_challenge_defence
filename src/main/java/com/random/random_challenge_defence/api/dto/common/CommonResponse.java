package com.random.random_challenge_defence.api.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {

    private boolean success;
    private String code;
    private String msg;
    private T data;
}
