package com.random.random_challenge_defence.domain.membermemberpersonality;

import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMemberPersonalityRepository extends JpaRepository<MemberMemberPersonality, Long> {

    @Query("select mmp from MemberMemberPersonality mmp where mmp.member.id = :id")
    List<MemberMemberPersonality> findAllByMemberId(Long id);
}
