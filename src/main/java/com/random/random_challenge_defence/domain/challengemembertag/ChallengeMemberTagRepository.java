package com.random.random_challenge_defence.domain.challengemembertag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeMemberTagRepository extends JpaRepository<ChallengeMemberTag, Long> {
}
