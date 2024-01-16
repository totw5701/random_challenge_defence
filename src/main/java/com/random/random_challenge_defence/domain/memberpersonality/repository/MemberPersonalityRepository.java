package com.random.random_challenge_defence.domain.memberpersonality.repository;

import com.random.random_challenge_defence.domain.memberpersonality.entity.MemberPersonality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPersonalityRepository extends JpaRepository<MemberPersonality, Long> {
}
