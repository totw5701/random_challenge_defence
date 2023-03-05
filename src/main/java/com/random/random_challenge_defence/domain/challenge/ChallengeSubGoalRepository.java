package com.random.random_challenge_defence.domain.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeSubGoalRepository extends JpaRepository<ChallengeSubGoal, Long> {
}
