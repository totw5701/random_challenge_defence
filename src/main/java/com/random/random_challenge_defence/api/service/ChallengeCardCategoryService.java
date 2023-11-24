package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeCardCategoryDetailDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeCardCategoryService {

    private final ChallengeCardCategoryRepository challengeCardCategoryRepository;

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
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CATEGORY);
        }
        return byId.get().toDetailDto();
    }
}
