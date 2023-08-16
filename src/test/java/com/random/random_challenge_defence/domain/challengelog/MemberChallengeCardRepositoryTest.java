package com.random.random_challenge_defence.domain.challengelog;

import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.member.MemberRole;
import com.random.random_challenge_defence.testtool.TestTools;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
class MemberChallengeCardRepositoryTest {

    @Autowired
    ChallengeLogRepository memberChallengeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ChallengeCardRepository challengeCardRepository;

    private TestTools testTools = new TestTools();

    @BeforeEach
    public void insertDummy() {

    }



}