package com.random.random_challenge_defence.api.dto.challenge;

import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallengeStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Setter
@Builder
@Getter
public class ChallengeTryUpdateDto {

    private Long id;
    private String evidence;
    private MemberChallengeStatus status;
    private String review;
}
