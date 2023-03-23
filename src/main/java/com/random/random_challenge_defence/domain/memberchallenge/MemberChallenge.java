package com.random.random_challenge_defence.domain.memberchallenge;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeTryUpdateDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MemberChallenge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String evidence;

    @Enumerated(EnumType.STRING)
    private MemberChallengeStatus status;
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    public void update(ChallengeTryUpdateDto form) {
        if(form.getEvidence() != null) {
            this.evidence = form.getEvidence();
        }
        if(form.getStatus() != null) {
            this.status = form.getStatus();
        }
        if(form.getReview() != null) {
            this.review = form.getReview();
        }
    }


}
