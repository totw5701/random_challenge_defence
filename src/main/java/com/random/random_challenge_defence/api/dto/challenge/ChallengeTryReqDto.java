package com.random.random_challenge_defence.api.dto.challenge;

import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeTryReqDto {

    private Long memberId;
    private Long challengeId;
}
