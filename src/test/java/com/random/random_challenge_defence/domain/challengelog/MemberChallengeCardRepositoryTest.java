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

    @Test
    public void findByMemberIdAndChallengeId() {
        // Given
        Member member = testTools.createDummyMember(MemberRole.USER, 1L, "email@email.com");
        ChallengeCard challenge = ChallengeCard.builder()
                .id(1L)
                .build();
        memberRepository.save(member);

        ChallengeLog memberChallenge = ChallengeLog.builder()
                .id(1L)
                .member(member)
                .evidence("evidence")
                .review("review")
                .status(ChallengeLogStatus.READY)
                .build();
        challengeCardRepository.save(challenge);

        memberChallengeRepository.save(memberChallenge);

        // When
        Optional<ChallengeLog> result = memberChallengeRepository.findByMemberIdAndChallengeId(member.getId(), challenge.getId());

        // Then
        Assertions.assertThat(result.get().getEvidence()).isEqualTo("evidence");
    }

}