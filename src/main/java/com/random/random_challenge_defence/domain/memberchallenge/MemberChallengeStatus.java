package com.random.random_challenge_defence.domain.memberchallenge;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberChallengeStatus {

    READY("R"),
    SUCCESS("S"),
    ACTION("A"),
    PASS("P");

    private final String code;

}
