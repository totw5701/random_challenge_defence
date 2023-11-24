package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.api.dto.member.MemberPutReqDto;
import com.random.random_challenge_defence.config.auth.oauth2.OAuthAttributes;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public String getLoginUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.entityUpdate(attributes.getPicture()))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }

    public Member getLoginMember() {
        return findByEmail(getLoginUserEmail());
    }

    public Member join(MemberPutReqDto form) {
        return memberRepository.save(
                Member.builder()
                        .joinDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                        .modifyDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                        .email(form.getEmail())
                        .memberRole(MemberRole.USER)
                        .nickname(form.getNickname())
                        .picture(form.getPicture())
                        .password(form.getPassword())
                        .build());
    }

    public Member findByEmail(String memberEmail) {
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

    public Member findById(Long memberId) {
        Optional<Member> opMember = memberRepository.findById(memberId);
        if(!opMember.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_MEMBER);
        }
        return opMember.get();
    }

}
