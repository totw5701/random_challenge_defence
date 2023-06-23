package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoalRepository;
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

    public Page<ChallengeDetailDto> readPageList(Integer nowPage) {
        Pageable pageable = PageRequest.of(nowPage, 15, Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<ChallengeCard> challenges = challengeCardRepository.findAll(pageable);

        // dto 변환
        List<ChallengeDetailDto> challengeDtoList = challenges.stream()
                .map(challenge -> challenge.toDetailDto())
                .collect(Collectors.toList());

        Page<ChallengeDetailDto> challengeDtoPage = new PageImpl<>(challengeDtoList, challenges.getPageable(), challenges.getTotalElements());

        return challengeDtoPage;
    }

    public ChallengeDetailDto create(ChallengePutReqDto form) {

        ChallengeCard challenge = ChallengeCard.builder()
                .assignScore(form.getAssignScore())
                .title(form.getTitle())
                .difficulty(form.getDifficulty())
                .description(form.getDescription())
                .evidenceType(form.getEvidenceType())
                .finalGoal(form.getFinalGoal())
                .createDtm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
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
        return challenge;
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
