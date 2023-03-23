package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CChallengeNotFoundException;
import com.random.random_challenge_defence.advice.exception.CMemberNotFoundException;
import com.random.random_challenge_defence.advice.exception.CNotFoundException;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeTryReqDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeTryUpdateDto;
import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.challenge.ChallengeRepository;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallenge;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallengeRepository;
import com.random.random_challenge_defence.domain.memberchallenge.MemberChallengeStatus;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberChallengeService {

    private final MemberChallengeRepository memberChallengeRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    public MemberChallenge create(ChallengeTryReqDto form) {

        Optional<Challenge> challengeOp = challengeRepository.findById(form.getChallengeId());
        if(!challengeOp.isPresent()) {
            throw new CChallengeNotFoundException();
        }

        Optional<Member> memberOp = memberRepository.findById(form.getMemberId());
        if(!memberOp.isPresent()) {
            throw new CMemberNotFoundException();
        }

        MemberChallenge challengeTry = MemberChallenge.builder()
                .member(memberOp.get())
                .challenge(challengeOp.get())
                .status(MemberChallengeStatus.READY)
                .build();
        return memberChallengeRepository.save(challengeTry);
    }

    public MemberChallenge readByMemberChallenge(Long memberId, Long challengeId) {
        Optional<MemberChallenge> memberChallengeOp = memberChallengeRepository.findByMemberIdAndChallengeId(memberId, challengeId);
        if(!memberChallengeOp.isPresent()){
            throw new CNotFoundException("MemberChallenge가 존재하지 않습니다.");
        }
        return memberChallengeOp.get();
    }

    public MemberChallenge read(Long id) {
        Optional<MemberChallenge> challengeTryOp = memberChallengeRepository.findById(id);
        if(!challengeTryOp.isPresent()){
            throw new CNotFoundException("MemberChallenge가 존재하지 않습니다.");
        }
        return challengeTryOp.get();
    }

    public MemberChallenge update(ChallengeTryUpdateDto form) {

        Optional<MemberChallenge> challengeTryOp = memberChallengeRepository.findById(form.getId());
        if(!challengeTryOp.isPresent()){
            throw new CChallengeNotFoundException();
        }
        challengeTryOp.get().update(form);
        return challengeTryOp.get();
    }

}
