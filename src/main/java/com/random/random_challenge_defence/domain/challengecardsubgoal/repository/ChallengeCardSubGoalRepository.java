package com.random.random_challenge_defence.domain.challengecardsubgoal.repository;

import com.random.random_challenge_defence.domain.challengecardsubgoal.entity.ChallengeCardSubGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeCardSubGoalRepository extends JpaRepository<ChallengeCardSubGoal, Long> {
}
