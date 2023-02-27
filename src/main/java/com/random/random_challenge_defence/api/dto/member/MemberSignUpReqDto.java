package com.random.random_challenge_defence.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpReqDto {

    private String email;
    private String nickname;
    private String picture;
    private String password;
    private String password2;


}
