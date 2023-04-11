package com.random.random_challenge_defence.domain.challengelog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogUpdateDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Challenge 객체를 JSON으로 바꿀때 다른 객체에게 참조 받는 경우 ID 만 넘긴다.
public class ChallengeLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String evidence;

    @Enumerated(EnumType.STRING)
    private ChallengeLogStatus status;
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "challenge_log_id")
//    private List<ChallengeLogSubGoal> challengeLogSubGoals;

    public ChallengeLogDetailDto toDetailDto() {
        return ChallengeLogDetailDto.builder()
                .id(this.id)
                .evidence(this.evidence)
                .status(this.status)
                .review(this.review)
                .memberId(this.member.getId())
                .challengeId(this.challenge.getId())
                .build();
    }

    public void update(ChallengeLogUpdateDto form) {
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
