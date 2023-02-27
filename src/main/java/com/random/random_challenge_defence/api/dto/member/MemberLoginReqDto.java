package com.random.random_challenge_defence.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberLoginReqDto {

    private String email;
    private String password;
}
