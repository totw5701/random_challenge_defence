package com.random.random_challenge_defence.domain.challengecardfeedback;

import com.random.random_challenge_defence.api.dto.challengecardfeedback.ChallengeCardFeedbackDetailDto;
import com.random.random_challenge_defence.api.dto.challengecardfeedback.ChallengeCardFeedbackReqDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChallengeCardFeedback {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_card")
    private ChallengeCard challengeCard;

    private String review;
    private int rating;

    public ChallengeCardFeedbackDetailDto toDetailDto() {
        ChallengeCardFeedbackDetailDto dto = ChallengeCardFeedbackDetailDto.builder()
                .challengeCardId(this.challengeCard.getId())
                .review(this.review)
                .rating(this.rating)
                .build();
        return dto;
    }

    public void update(ChallengeCardFeedbackReqDto form) {
        if(!StringUtils.isEmpty(form.getReview())) this.review = form.getReview();
        this.rating = form.getRating();
    }
}
