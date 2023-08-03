package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategoryRepository;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoalRepository;
import com.random.random_challenge_defence.domain.file.S3File;
import com.random.random_challenge_defence.domain.file.S3FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeCardService {

    private final ChallengeCardRepository challengeCardRepository;
    private final ChallengeCardSubGoalRepository challengeCardSubGoalRepository;
    private final ChallengeCardCategoryRepository challengeCardCategoryRepository;
    private final S3FileRepository s3FileRepository;

    public Page<ChallengeDetailDto> readPageList(Integer nowPage) {
        Pageable pageable = PageRequest.of(nowPage, 15, Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<ChallengeCard> challenges = challengeCardRepository.findAll(pageable);

        // dto 변환
        List<ChallengeDetailDto> challengeCardDtoList = challenges.stream()
                .map(challenge -> challenge.toDetailDto())
                .collect(Collectors.toList());

        Page<ChallengeDetailDto> challengeDtoPage = new PageImpl<>(challengeCardDtoList, challenges.getPageable(), challenges.getTotalElements());

        return challengeDtoPage;
    }

    public ChallengeDetailDto create(ChallengePutReqDto form) {

        ChallengeCardCategory challengeCardCategory = challengeCardCategoryRepository.findById(form.getChallengeCardCategoryId()).get();
        S3File image = s3FileRepository.findById(form.getId()).get();

        ChallengeCard challenge = ChallengeCard.builder()
                .assignScore(form.getAssignScore())
                .title(form.getTitle())
                .difficulty(form.getDifficulty())
                .description(form.getDescription())
                .evidenceType(form.getEvidenceType())
                .finalGoal(form.getFinalGoal())
                .createDtm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .challengeCardCategory(challengeCardCategory)
                .image(image)
                .build();

        List<ChallengeCardSubGoal> subGoals = form.getChallengeSubGoals().stream()
                .map(subGoalDto -> ChallengeCardSubGoal.builder()
                        .challengeCard(challenge)
                        .subGoal(subGoalDto)
                        .build())
                .collect(Collectors.toList());
        challenge.assignSubGoals(subGoals);


        challengeCardRepository.save(challenge);
        return challenge.toDetailDto();
    }

    public ChallengeDetailDto readOne(Long id){
        Optional<ChallengeCard> opChallenge = challengeCardRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        return opChallenge.get().toDetailDto();
    }

    public ChallengeCard update(ChallengePutReqDto form) {
        Optional<ChallengeCard> opChallenge = challengeCardRepository.findById(form.getId());
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        ChallengeCard challenge = opChallenge.get();
        challenge.update(form);

        if(form.getImage() != null) {
            S3File image = s3FileRepository.findById(form.getImage()).get();
            challenge.imageUpdate(image);
        }
        return challenge;
    }

    public void updateCategory(Long cardId, Long categoryId) {
        ChallengeCardCategory challengeCardCategory = challengeCardCategoryRepository.findById(categoryId).get();
        ChallengeCard challengeCard = challengeCardRepository.findById(cardId).get();
        challengeCard.updateChallengeCardCategory(challengeCardCategory);

    }

    public void delete(Long id) {
        Optional<ChallengeCard> opChallenge = challengeCardRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        challengeCardRepository.delete(opChallenge.get());
    }

    public ChallengeCard findById(Long id) {
        Optional<ChallengeCard> opChallenge = challengeCardRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        return opChallenge.get();
    }
}
