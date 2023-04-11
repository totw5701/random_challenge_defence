package com.random.random_challenge_defence.domain.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeSubGoalRepository extends JpaRepository<ChallengeSubGoal, Long> {


    @Query("select csg from ChallengeSubGoal csg where csg.id = :subGoalId")
    void findMemberChallenge(@Param("subGoalId") Long subGoalId);
}
