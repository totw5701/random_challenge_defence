package com.random.random_challenge_defence.domain.challengeCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeCardRepository extends JpaRepository<ChallengeCard, Long> {

    Long countBy();
}
