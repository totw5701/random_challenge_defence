package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.exception.CAccessDeniedException;
import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalUpdateDto;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeLogSubGoalStatus;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeLogSubGoalService {

    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;
    private final MemberService memberService;

    @Transactional
    public ChallengeLogSubGoal updateSubGoal(ChallengeLogSubGoal challengeLogSubGoal, ChallengeLogSubGoalStatus status) {
        challengeLogSubGoal.statusChange(status);
        return challengeLogSubGoal;
    }

    public ChallengeLogSubGoal getChallengeLogSubGoal(Long id) {
        Optional<ChallengeLogSubGoal> byId = challengeLogSubGoalRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CNotFoundException("존재하지 않는 sub-goal 입니다.");
        }
        return byId.get();
    }
}
