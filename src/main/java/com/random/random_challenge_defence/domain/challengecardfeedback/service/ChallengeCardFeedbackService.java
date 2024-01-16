package com.random.random_challenge_defence.domain.challengecardfeedback.service;

import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.challengecardfeedback.dto.ChallengeCardFeedbackReqDto;
import com.random.random_challenge_defence.domain.challengecard.entity.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.repository.ChallengeCardRepository;
import com.random.random_challenge_defence.domain.challengecardfeedback.entity.ChallengeCardFeedback;
import com.random.random_challenge_defence.domain.challengecardfeedback.repository.ChallengeCardFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCardFeedbackService {

    private final ChallengeCardRepository challengeCardRepository;
    private final ChallengeCardFeedbackRepository challengeCardFeedbackRepository;

    @Transactional
    public ChallengeCardFeedback create(ChallengeCardFeedbackReqDto form) {

        Optional<ChallengeCard> opChallengeCard = challengeCardRepository.findById(form.getChallengeCardId());
        if(!opChallengeCard.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CARD);
        }

        ChallengeCardFeedback feedback = ChallengeCardFeedback.builder()
                .challengeCard(opChallengeCard.get())
                .review(form.getReview())
                .rating(form.getRating())
                .build();

        return challengeCardFeedbackRepository.save(feedback);
    }

    @Transactional
    public ChallengeCardFeedback update(ChallengeCardFeedbackReqDto form) {
        Optional<ChallengeCardFeedback> opFeedback = challengeCardFeedbackRepository.findById(form.getId());
        if(!opFeedback.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_FEEDBACK);
        }

        ChallengeCardFeedback challengeCardFeedback = opFeedback.get();
        challengeCardFeedback.update(form);
        return challengeCardFeedback;
    }
}
