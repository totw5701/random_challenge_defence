package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.advice.exception.CMemberNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeTryReqDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.challenge.ChallengeRepository;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallenge;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallengeRepository;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallengeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MemberChallengeServiceTest {

    @Mock
    private MemberChallengeRepository memberChallengeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private MemberChallengeService memberChallengeService;

    @Test
    void testCreate() {
        // given
        Long memberId = 1L;
        Long challengeId = 2L;

        Member member = Member.builder().id(memberId).build();
        Challenge challenge = Challenge.builder().id(challengeId).build();

        ChallengeTryReqDto dto = new ChallengeTryReqDto();
        dto.setMemberId(memberId);
        dto.setChallengeId(challengeId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(memberChallengeRepository.save(any(MemberChallenge.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        MemberChallenge result = memberChallengeService.create(dto);

        // then
        assertEquals(member, result.getMember());
        assertEquals(challenge, result.getChallenge());
        assertEquals(MemberChallengeStatus.READY, result.getStatus());
    }

    @Test
    void testCreateWithInvalidChallengeId() {
        // given
        Long memberId = 1L;
        Long challengeId = 2L;

        ChallengeTryReqDto dto = new ChallengeTryReqDto();
        dto.setMemberId(memberId);
        dto.setChallengeId(challengeId);

        when(challengeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(CChallengeNotFoundException.class, () -> memberChallengeService.create(dto));
    }

    @Test
    void testCreateWithDatabaseError() {
        // given
        Long memberId = 1L;
        Long challengeId = 2L;

        Member member = Member.builder().id(memberId).build();
        Challenge challenge = Challenge.builder().id(challengeId).build();

        ChallengeTryReqDto dto = new ChallengeTryReqDto();
        dto.setMemberId(memberId);
        dto.setChallengeId(challengeId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(memberChallengeRepository.save(any(MemberChallenge.class))).thenThrow(DataIntegrityViolationException.class);

        // then
        assertThrows(DataIntegrityViolationException.class, () -> memberChallengeService.create(dto));
    }

    @Test
    public void testReadByMemberChallenge() {
        // Given
        Long memberId = 1L;
        Long challengeId = 2L;
        MemberChallenge memberChallenge = MemberChallenge.builder().id(3L).build();
        when(memberChallengeRepository.findByMemberIdAndChallengeId(anyLong(), anyLong())).thenReturn(Optional.of(memberChallenge));

        // When
        MemberChallenge result = memberChallengeService.readByMemberChallenge(memberId, challengeId);

        // Then
        Assertions.assertEquals(memberChallenge, result);
    }

}