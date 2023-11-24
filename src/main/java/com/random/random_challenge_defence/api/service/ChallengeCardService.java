package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
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
            throw new CustomException(ExceptionCode.NOT_FOUND_FILE);
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
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CARD);
        }
        return opChallenge.get().toDetailDto();
    }

    public ChallengeCard findById(Long id) {
        Optional<ChallengeCard> opChallenge = challengeCardRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CARD);
        }
        return opChallenge.get();
    }

    public ChallengeCard update(ChallengePutReqDto form) {
        ChallengeCard challenge = findById(form.getId());
        challenge.update(form);

        // 챌린지 카테고리 업데이트
        if(form.getChallengeCardCategoryId() != null) {
            Optional<ChallengeCardCategory> opChallengeCardCategory = challengeCardCategoryRepository.findById(form.getChallengeCardCategoryId());
            if(!opChallengeCardCategory.isPresent()) {
                throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CATEGORY);
            }
            challenge.updateChallengeCardCategory(opChallengeCardCategory.get());
        }

        // 챌린지 카드 중간목표 수정
        if(form.getChallengeSubGoals() != null) {
            List<ChallengeCardSubGoal> oldChallengeCardSubGoals = challenge.getChallengeCardSubGoals();

            List<ChallengeCardSubGoal> newChallengeCardSubGoals = form.getChallengeSubGoals().stream()
                    .map(subGoalDto -> ChallengeCardSubGoal.builder()
                            .challengeCard(challenge)
                            .subGoal(subGoalDto)
                            .build())
                    .collect(Collectors.toList());

            // 이전 중간 목표 삭제
            for(ChallengeCardSubGoal goal : oldChallengeCardSubGoals){
                challengeCardSubGoalRepository.delete(goal);
            }

            challenge.assignSubGoals(newChallengeCardSubGoals);
        }

        // 파일 업데이트
        if(form.getImage() != null && !form.getImage().equals(challenge.getImage().getId())) {
            // 기존 이미지 파일 삭제 기능 추가 구현
            fileRepository.delete(challenge.getImage()); //<<-- 삭제 해도 괜찮을까...?

            // 새로운 이미지 할당
            Optional<File> opImage = fileRepository.findById(form.getImage());
            if(!opImage.isPresent()) {
                throw new CustomException(ExceptionCode.NOT_FOUND_FILE);
            }
            challenge.imageUpdate(opImage.get());
            opImage.get().assignChallengeCard(challenge);
        }
        return challenge;
    }

    public void delete(Long id) {
        Optional<ChallengeCard> opChallenge = challengeCardRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CARD);
        }
        ChallengeCard challengeCard = opChallenge.get();
        challengeCardRepository.delete(challengeCard);
    }

}
