package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChallengeLogService {

    private final ChallengeLogRepository challengeLogRepository;
    private final ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    public ChallengeLog createChallengeLog(Member member, ChallengeCard challengeCard) {
        ChallengeLog challengeLog = ChallengeLog.builder()
                .challengeCard(challengeCard)
                .status(ChallengeLogStatus.READY)
                .member(member)
                .startDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .build();

        ChallengeLog saved = challengeLogRepository.save(challengeLog);

        createChallengeLogSubGoals(challengeCard, challengeLog);

        return saved;
    }

    public List<ChallengeLogSubGoal> createChallengeLogSubGoals(ChallengeCard challengeCard, ChallengeLog challengeLog) {
        List<ChallengeCardSubGoal> subGoals = challengeCard.getChallengeCardSubGoals();
        List<ChallengeLogSubGoal> logSubGoals = new ArrayList<>();

        for(ChallengeCardSubGoal subGoal : subGoals) {
            ChallengeLogSubGoal challengeLogSubGoal = ChallengeLogSubGoal.builder()
                    .challengeLog(challengeLog)
                    .challengeCardSubGoal(subGoal)
                    .challengeLogSubGoalStatus(ChallengeLogStatus.READY)
                    .build();
            logSubGoals.add(challengeLogSubGoalRepository.save(challengeLogSubGoal));
        }
        return logSubGoals;
    }

    public ChallengeLog getChallengeLogDetail(Long id) {
        return challengeLogRepository.getById(id);
    }
}
