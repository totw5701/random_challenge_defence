package com.random.random_challenge_defence.domain.challengecardcategory.repository;

import com.random.random_challenge_defence.domain.challengecardcategory.entity.ChallengeCardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeCardCategoryRepository extends JpaRepository<ChallengeCardCategory, Long> {

    @Query("select c.id from ChallengeCardCategory c")
    List<Long> findAllId();
}
