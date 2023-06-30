package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryUpdateDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeCardCategoryService {

    private final ChallengeCardCategoryRepository challengeCardCategoryRepository;

    @Transactional
    public void createCategory(ChallengeCardCategoryReqDto form) {
        ChallengeCardCategory category = ChallengeCardCategory.builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .build();

        challengeCardCategoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(ChallengeCardCategoryUpdateDto form) {
        Optional<ChallengeCardCategory> challengeCardCategoryOp = challengeCardCategoryRepository.findById(form.getId());
        if(!challengeCardCategoryOp.isPresent()) {
            throw new CNotFoundException("존재하지 않는 카테고리입니다.");
        }
        challengeCardCategoryOp.get().update(form);
    }

    public Page<ChallengeCardCategoryDetailDto> readPageList(Integer nowPage) {
        Pageable pageable = PageRequest.of(nowPage, 15, Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<ChallengeCardCategory> challengeCardCategories = challengeCardCategoryRepository.findAll(pageable);


        List<ChallengeCardCategoryDetailDto> challengeCardCategoryDtoList = challengeCardCategories.stream()
                .map(cardCategory -> cardCategory.toDetailDto())
                .collect(Collectors.toList());

        Page<ChallengeCardCategoryDetailDto> challengeDtoPage = new PageImpl<>(challengeCardCategoryDtoList,
                challengeCardCategories.getPageable(),
                challengeCardCategories.getTotalElements());

        return challengeDtoPage;

    }

    public ChallengeCardCategoryDetailDto readOne(String id) {
        Optional<ChallengeCardCategory> byId = challengeCardCategoryRepository.findById(Long.valueOf(id));
        if(!byId.isPresent()) {
            throw new CNotFoundException("챌린지 카테고리가 존재하지 않습니다.");
        }
        return byId.get().toDetailDto();
    }
}
