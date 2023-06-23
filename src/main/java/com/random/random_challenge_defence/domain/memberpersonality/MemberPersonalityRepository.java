package com.random.random_challenge_defence.domain.memberpersonality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPersonalityRepository extends JpaRepository<MemberPersonality, Long> {
}
