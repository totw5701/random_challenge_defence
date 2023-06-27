package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryUpdateDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCardCategoryService {

    private final ChallengeCardCategoryRepository challengeCardCategoryRepository;


    public void createCategory(ChallengeCardCategoryReqDto form) {

        ChallengeCardCategory category = ChallengeCardCategory.builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .build();

        challengeCardCategoryRepository.save(category);
    }

    public void updateCategory(ChallengeCardCategoryUpdateDto form) {
        Optional<ChallengeCardCategory> challengeCardCategoryOp = challengeCardCategoryRepository.findById(form.getId());
        if(!challengeCardCategoryOp.isPresent()) {
            throw new CNotFoundException("존재하지 않는 카테고리입니다.");
        }
        challengeCardCategoryOp.get().update(form);
    }
}
