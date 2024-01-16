package com.random.random_challenge_defence.domain.challengelogsubgoal.service;

import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.entity.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.repository.ChallengeLogSubGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeLogSubGoalService {

    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    public ChallengeLogSubGoal validateOwner(Long subGoalId, String memberEmail) {
        ChallengeLogSubGoal subGoal = getEntityById(subGoalId);
        if(!subGoal.getChallengeLog().getMember().getEmail().equals(memberEmail)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }
        return subGoal;
    }

    public ChallengeLogSubGoal updateSubGoal(ChallengeLogSubGoal challengeLogSubGoal, ChallengeLogSubGoalStatus status) {
        challengeLogSubGoal.statusChange(status);
        return challengeLogSubGoal;
    }

    public ChallengeLogSubGoal getEntityById(Long id) {
        Optional<ChallengeLogSubGoal> byId = challengeLogSubGoalRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_SUB_GOAL);
        }
        return byId.get();
    }
}
