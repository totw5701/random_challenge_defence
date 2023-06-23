package com.random.random_challenge_defence.domain.memberbadge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {
}
