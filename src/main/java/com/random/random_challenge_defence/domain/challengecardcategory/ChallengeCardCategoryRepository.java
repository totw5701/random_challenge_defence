package com.random.random_challenge_defence.domain.challengecardcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeCardCategoryRepository extends JpaRepository<ChallengeCardCategory, Long> {

    @Query("select c.id from ChallengeCardCategory c")
    List<Long> findAllId();
}
