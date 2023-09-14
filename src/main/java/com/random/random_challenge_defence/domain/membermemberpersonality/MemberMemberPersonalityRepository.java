package com.random.random_challenge_defence.domain.membermemberpersonality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMemberPersonalityRepository extends JpaRepository<MemberMemberPersonality, Long> {
}
