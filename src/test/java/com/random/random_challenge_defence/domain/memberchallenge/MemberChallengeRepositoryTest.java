package com.random.random_challenge_defence.domain.memberchallenge;

import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.challenge.ChallengeRepository;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.member.MemberRole;
import com.random.random_challenge_defence.testtool.TestTools;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberChallengeRepositoryTest {

    @Autowired
    MemberChallengeRepository memberChallengeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ChallengeRepository challengeRepository;

    private TestTools testTools = new TestTools();

    @BeforeEach
    public void insertDummy() {

    }

    @Test
    public void findByMemberIdAndChallengeId() {
        // Given
        Member member = testTools.createDummyMember(MemberRole.USER, 1L, "email@email.com");
        Challenge challenge = Challenge.builder()
                .id(1L)
                .build();
        memberRepository.save(member);

        MemberChallenge memberChallenge = MemberChallenge.builder()
                .id(1L)
                .member(member)
                .challenge(challenge)
                .evidence("evidence")
                .review("review")
                .status(MemberChallengeStatus.READY)
                .build();
        challengeRepository.save(challenge);

        memberChallengeRepository.save(memberChallenge);

        // When
        Optional<MemberChallenge> result = memberChallengeRepository.findByMemberIdAndChallengeId(member.getId(), challenge.getId());

        // Then
        Assertions.assertThat(result.get().getEvidence()).isEqualTo("evidence");
    }

}