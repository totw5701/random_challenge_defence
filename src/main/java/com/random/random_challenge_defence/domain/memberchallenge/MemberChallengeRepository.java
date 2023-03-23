package com.random.random_challenge_defence.domain.memberchallenge;

import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {

    @Query("select mc from MemberChallenge mc where mc.member.id = :memberId and mc.challenge.id = :challengeId")
    Optional<MemberChallenge> findByMemberIdAndChallengeId (@Param("memberId") Long memberId, @Param("challengeId") Long challengeId);
}
