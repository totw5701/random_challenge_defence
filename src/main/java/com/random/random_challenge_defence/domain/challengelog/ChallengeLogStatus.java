package com.random.random_challenge_defence.domain.challengelog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChallengeLogStatus {

    READY("R"),
    SUCCESS("S"),
    ACTION("A"),
    PASS("P");

    private final String code;

}
