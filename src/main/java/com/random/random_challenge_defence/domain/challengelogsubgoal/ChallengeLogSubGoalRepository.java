package com.random.random_challenge_defence.domain.challengelogsubgoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChallengeLogSubGoalRepository extends JpaRepository<ChallengeLogSubGoal, Long> {

    @Query("select c.challengeLogSubGoals from ChallengeLog c where c.id = :challengeLogId")
    List<ChallengeLogSubGoal> getListByChallengeLogId(@Param("challengeLogId") Long challengeLogId);
}
