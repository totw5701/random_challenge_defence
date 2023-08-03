package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoalRepository;
import com.random.random_challenge_defence.domain.file.S3File;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRole;
import com.random.random_challenge_defence.testtool.TestTools;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChallengeLogServiceTest {

    @Mock
    private ChallengeLogRepository challengeLogRepository;

    @Mock
    private ChallengeLogSubGoalRepository challengeLogSubGoalRepository;

    @InjectMocks
    private ChallengeLogService challengeLogService;

    private TestTools testTools = new TestTools();


    @Test
    void testCreateChallengeLog() {

        // Given
        Member member = testTools.createDummyMember(MemberRole.USER, 1L, "email@email.com");
        S3File s3File = testTools.createDummyS3File();
        ChallengeCardCategory challengeCardCategory = testTools.createChallengeCardCategory();
        ChallengeCard challengeCard = testTools.createDummyChallengeCard(s3File, challengeCardCategory);
        ChallengeLog challengeLog = testTools.createDummyChallengeLog(member, challengeCard);


        // When
        when(challengeLogRepository.save(any(ChallengeLog.class))).thenReturn(challengeLog);
        ChallengeLog result = challengeLogService.createChallengeLog(member, challengeCard);

        // Then
        verify(challengeLogRepository, times(1))
                .save(any(ChallengeLog.class));

        Assertions.assertThat(result.getChallengeCard()).isEqualTo(challengeCard);
        Assertions.assertThat(result.getMember()).isEqualTo(member);
        Assertions.assertThat(result.getStatus()).isEqualTo(ChallengeLogStatus.READY);
    }

    @Test
    void testCreateChallengeLogSubGoals() {

        // Given

        // When

        // Then


    }
}