package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogReqDto;
import com.random.random_challenge_defence.api.service.ChallengeLogService;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MemberChallengeCardServiceTest {

    @Mock
    private ChallengeLogRepository memberChallengeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ChallengeCardRepository challengeCardRepository;

    @InjectMocks
    private ChallengeLogService challengeLogService;

    //@Test
    void testCreate() {
        // given
        Long memberId = 1L;
        Long challengeId = 2L;

        Member member = Member.builder().id(memberId).build();
        ChallengeCard challenge = ChallengeCard.builder().id(challengeId).build();

        ChallengeLogReqDto dto = new ChallengeLogReqDto();
        dto.setChallengeId(challengeId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(challengeCardRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(memberChallengeRepository.save(any(ChallengeLog.class))).thenAnswer(i -> i.getArguments()[0]);

        // when

        // then
    }

    //@Test
    void testCreateWithInvalidChallengeId() {
        // given
        Long memberId = 1L;
        Long challengeId = 2L;

        ChallengeLogReqDto dto = new ChallengeLogReqDto();
        dto.setChallengeId(challengeId);

        when(challengeCardRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
    }

    //@Test
    void testCreateWithDatabaseError() {
        // given
        Long memberId = 1L;
        Long challengeId = 2L;

        Member member = Member.builder().id(memberId).build();
        ChallengeCard challenge = ChallengeCard.builder().id(challengeId).build();

        ChallengeLogReqDto dto = new ChallengeLogReqDto();
        dto.setChallengeId(challengeId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(challengeCardRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(memberChallengeRepository.save(any(ChallengeLog.class))).thenThrow(DataIntegrityViolationException.class);

        // then
    }

    //@Test
    public void testReadByMemberChallenge() {
        // Given
        Long memberId = 1L;
        Long challengeId = 2L;
        ChallengeLog memberChallenge = ChallengeLog.builder().id(3L).build();
        when(memberChallengeRepository.findByMemberIdAndChallengeId(anyLong(), anyLong())).thenReturn(Optional.of(memberChallenge));

        // When

        // Then
    }

}