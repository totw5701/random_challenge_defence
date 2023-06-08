package com.random.random_challenge_defence.domain.challengecardsubgoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeCardSubGoalRepository extends JpaRepository<ChallengeCardSubGoal, Long> {


    @Query("select csg from ChallengeCardSubGoal csg where csg.id = :subGoalId")
    void findMemberChallenge(@Param("subGoalId") Long subGoalId);
}
