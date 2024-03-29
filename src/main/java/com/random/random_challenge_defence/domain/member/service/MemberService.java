package com.random.random_challenge_defence.domain.member.service;

import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.member.dto.MemberPutReqDto;
import com.random.random_challenge_defence.global.config.auth.oauth2.OAuthAttributes;
import com.random.random_challenge_defence.domain.challengelog.repository.ChallengeLogRepository;
import com.random.random_challenge_defence.domain.member.entity.Member;
import com.random.random_challenge_defence.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final ChallengeLogRepository challengeLogRepository;

    public String getLoginUserEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(StringUtils.isBlank(email)) {
            throw new CustomException(ExceptionCode.NOT_FOUND_MEMBER);
        }
        return email;
    }

    public void verifyChallengeLogAvailability(String memberEmail) {
        Long numOfTrying = challengeLogRepository.getNumOfTrying(memberEmail);
        if(numOfTrying >= 2) {
            throw new CustomException(ExceptionCode.SERVICE_USAGE_LIMIT_EXCEEDED);
        }
    }

    public Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.entityUpdate(attributes.getPicture()))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }

    public Member getLoginMember() {
        return getEntityById(getLoginUserEmail());
    }

    public Member getEntityById(String memberEmail) {
        Optional<Member> opMember = memberRepository.findByEmail(memberEmail);
        if(!opMember.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_MEMBER);
        }
        return opMember.get();
    }

    public Member update(MemberPutReqDto form) {
        Optional<Member> opMember = memberRepository.findById(form.getId());
        if(!opMember.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_MEMBER);
        }
        return opMember.get().update(form);
    }

    public void delete(Long memberId) {
        Optional<Member> opMember = memberRepository.findById(memberId);
        if(!opMember.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_MEMBER);
        }
        memberRepository.delete(opMember.get());
    }
}
