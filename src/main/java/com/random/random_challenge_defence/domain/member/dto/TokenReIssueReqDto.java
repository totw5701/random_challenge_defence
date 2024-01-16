package com.random.random_challenge_defence.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TokenReIssueReqDto {

    private String refreshToken;
}
