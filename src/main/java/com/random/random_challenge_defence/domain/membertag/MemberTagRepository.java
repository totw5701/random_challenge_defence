package com.random.random_challenge_defence.domain.membertag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTagRepository extends JpaRepository<MemberTag, Long> {
}
