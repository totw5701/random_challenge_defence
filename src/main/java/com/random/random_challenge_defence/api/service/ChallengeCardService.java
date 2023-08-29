package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengeDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.api.dto.challengeCard.ChallengePutReqDto;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategoryRepository;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoalRepository;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.file.FileRepository;
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
    private final FileRepository fileRepository;

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
        Optional<File> opImage = fileRepository.findById(form.getImage());

        if(!opImage.isPresent()) {
            throw new CNotFoundException("file이 존재하지 않습니다.");
        }

        File image = opImage.get();

        // 챌린지 카드 생성
        ChallengeCard challengeCard = ChallengeCard.builder()
                .assignScore(form.getAssignScore())
                .title(form.getTitle())
                .difficulty(form.getDifficulty())
                .description(form.getDescription())
                .finalGoal(form.getFinalGoal())
                .createDtm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .challengeCardCategory(challengeCardCategory)
                .experience(form.getExperience())
                .image(image)
                .build();

        // 챌린지 카드 중간 도전 리스트 생성 및 할당
        List<ChallengeCardSubGoal> subGoals = form.getChallengeSubGoals().stream()
                .map(subGoalDto -> ChallengeCardSubGoal.builder()
                        .challengeCard(challengeCard)
                        .subGoal(subGoalDto)
                        .build())
                .collect(Collectors.toList());
        challengeCard.assignSubGoals(subGoals);

        ChallengeCard save = challengeCardRepository.save(challengeCard);

        // 이미지 할당
        image.assignChallengeCard(save);
        return challengeCard.toDetailDto();
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
            File image = fileRepository.findById(form.getImage()).get();
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
