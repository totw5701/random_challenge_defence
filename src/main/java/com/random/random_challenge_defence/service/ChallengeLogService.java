package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.advice.exception.CMemberNotFoundException;
import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeLogUpdateDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.challenge.ChallengeRepository;
import com.random.random_challenge_defence.domain.challenge.ChallengeSubGoalRepository;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeLogService {

    private final ChallengeLogRepository challengeLogRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeSubGoalRepository challengeSubGoalRepository;

    public ChallengeLogDetailDto create(ChallengeLogReqDto form) {

        Optional<Challenge> challengeOp = challengeRepository.findById(form.getChallengeId());
        if(!challengeOp.isPresent()) {
            throw new CChallengeNotFoundException();
        }

        Optional<Member> memberOp = memberRepository.findById(form.getMemberId());
        if(!memberOp.isPresent()) {
            throw new CMemberNotFoundException();
        }

        ChallengeLog challengeLog = ChallengeLog.builder()
                .member(memberOp.get())
                .challenge(challengeOp.get())
                .status(ChallengeLogStatus.READY)
                .build();
        challengeLogRepository.save(challengeLog);

        return challengeLog.toDetailDto();
    }

    public ChallengeLog readByMemberChallenge(Long memberId, Long challengeId) {
        Optional<ChallengeLog> memberChallengeOp = challengeLogRepository.findByMemberIdAndChallengeId(memberId, challengeId);
        if(!memberChallengeOp.isPresent()){
            throw new CNotFoundException("MemberChallenge가 존재하지 않습니다.");
        }
        return memberChallengeOp.get();
    }

    public ChallengeLogDetailDto read(Long id) {
        Optional<ChallengeLog> challengeTryOp = challengeLogRepository.findById(id);
        if(!challengeTryOp.isPresent()){
            throw new CNotFoundException("MemberChallenge가 존재하지 않습니다.");
        }
        return challengeTryOp.get().toDetailDto();
    }

    public ChallengeLog update(ChallengeLogUpdateDto form) {

        Optional<ChallengeLog> challengeTryOp = challengeLogRepository.findById(form.getId());
        if(!challengeTryOp.isPresent()){
            throw new CChallengeNotFoundException();
        }
        challengeTryOp.get().update(form);
        return challengeTryOp.get();
    }


}
