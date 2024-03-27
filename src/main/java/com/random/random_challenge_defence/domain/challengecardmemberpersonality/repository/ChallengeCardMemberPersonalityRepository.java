package com.random.random_challenge_defence.domain.challengecardmemberpersonality.repository;

import com.random.random_challenge_defence.domain.challengecardmemberpersonality.entity.ChallengeCardMemberPersonality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeCardMemberPersonalityRepository extends JpaRepository<ChallengeCardMemberPersonality, Long> {
}
