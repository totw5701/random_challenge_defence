package com.random.random_challenge_defence.domain.challengelog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLog, Long> {

    @Query("select cl from ChallengeLog cl where cl.member.id = :memberId and cl.challengeCard.id = :challengeId")
    Optional<ChallengeLog> findByMemberIdAndChallengeId (@Param("memberId") Long memberId, @Param("challengeId") Long challengeId);
}
