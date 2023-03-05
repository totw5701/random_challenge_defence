package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeDetailDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.challenge.ChallengeRepository;
import com.random.random_challenge_defence.api.dto.challenge.ChallengePutReqDto;
import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoal;
import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeSubGoalRepository challengeSubGoalRepository;

    public Page<ChallengeDetailDto> readPageList(Integer nowPage) {
        Pageable pageable = PageRequest.of(nowPage, 15, Sort.by("id").descending()); // 한 페이지에 15개씩 출력
        Page<Challenge> challenges = challengeRepository.findAll(pageable);

        // dto 변환
        List<ChallengeDetailDto> challengeDtoList = challenges.stream()
                .map(challenge -> challenge.toDto())
                .collect(Collectors.toList());

        Page<ChallengeDetailDto> challengeDtoPage = new PageImpl<>(challengeDtoList, challenges.getPageable(), challenges.getTotalElements());

        return challengeDtoPage;
    }

    public Challenge create(ChallengePutReqDto form) {

        Challenge challenge = Challenge.builder()
                .assignScore(form.getAssignScore())
                .title(form.getTitle())
                .description(form.getDescription())
                .evidenceType(form.getEvidenceType())
                .finalGoal(form.getFinalGoal())
                .createDate(new Date().getTime())
                .build();

        List<ChallengeSubGoal> subGoals = form.getChallengeSubGoals().stream()
                .map(subGoalDto -> ChallengeSubGoal.builder()
                        .challenge(challenge)
                        .intermediateGoal(subGoalDto)
                        .build())
                .collect(Collectors.toList());
        challenge.assignSubGoals(subGoals);

        return challengeRepository.save(challenge);
    }

    public ChallengeDetailDto readOne(Long id){
        Optional<Challenge> opChallenge = challengeRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        return opChallenge.get().toDto();
    }

    public Challenge update(ChallengePutReqDto form) {
        Optional<Challenge> opChallenge = challengeRepository.findById(form.getId());
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        Challenge challenge = opChallenge.get();
        challenge.update(form);
        return challenge;
    }

    public void delete(Long id) {
        Optional<Challenge> opChallenge = challengeRepository.findById(id);
        if(!opChallenge.isPresent()) {
            throw new CChallengeNotFoundException();
        }
        challengeRepository.delete(opChallenge.get());
    }
}
