package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalUpdateDto;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeLogSubGoalService {

    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    public ChallengeLogSubGoal updateSubGoal(ChallengeLogSubGoalUpdateDto reqDto) {
        Optional<ChallengeLogSubGoal> byId = challengeLogSubGoalRepository.findById(reqDto.getId());
        if(!byId.isPresent()) {
            throw new CNotFoundException("존재하지 않는 subgoal 입니다.");
        }
        ChallengeLogSubGoal challengeLogSubGoal = byId.get();
        challengeLogSubGoal.statusChange(reqDto.getStatus());
        return challengeLogSubGoal;
    }
}
