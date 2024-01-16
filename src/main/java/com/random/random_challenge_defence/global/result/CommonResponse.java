package com.random.random_challenge_defence.global.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private boolean success;
    private String code;
    private String msg;
    private T data;

}
