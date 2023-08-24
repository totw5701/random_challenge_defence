package com.random.random_challenge_defence.domain.challengelog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLog, Long> {

    @Query("select cl from ChallengeLog cl where cl.member.id = :memberId and cl.challengeCard.id = :challengeId")
    Optional<ChallengeLog> findByMemberIdAndChallengeId (@Param("memberId") Long memberId, @Param("challengeId") Long challengeId);

    @Query("select cl from ChallengeLog cl where cl.member.email = :memberEmail")
    Page<ChallengeLog> findAllByEmail(@Param("memberEmail") String memberEmail, Pageable pageable);

    @Query("select cl from ChallengeLog cl where cl.member.email = :memberEmail and cl.status = 'READY'")
    List<ChallengeLog> findAllByEmailTrying(@Param("memberEmail") String memberEmail);

    @Query("select COUNT(cl) from ChallengeLog cl where cl.member.email = :memberEmail and cl.status in ('READY', 'ACTION')")
    Long getNumOfTrying(@Param("memberEmail") String memberEmail);

    @Query("select cl from ChallengeLog cl where cl.status = 'PAUSE' and cl.member.email = :memberEmail and cl.challengeCard.id = :challengeId")
    Optional<ChallengeLog> findPausedLogByMemberEmailAndChallengeId(String memberEmail, Long challengeId);
}
