package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeLogSubGoalService {

    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    @Transactional
    public ChallengeLogSubGoal updateSubGoal(ChallengeLogSubGoal challengeLogSubGoal, ChallengeLogSubGoalStatus status) {
        challengeLogSubGoal.statusChange(status);
        return challengeLogSubGoal;
    }

    public ChallengeLogSubGoal getChallengeLogSubGoal(Long id) {
        Optional<ChallengeLogSubGoal> byId = challengeLogSubGoalRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_SUB_GOAL);
        }
        return byId.get();
    }
}
