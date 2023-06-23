package com.random.random_challenge_defence.domain.challengecard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeCardRepository extends JpaRepository<ChallengeCard, Long> {

    Long countBy();

    @Query("select c.id from ChallengeCard c where c.difficulty <= :memberLevel")
    List<Long> findAllUnderMemberLever(@Param("memberLevel") Integer memberLevel);

}
