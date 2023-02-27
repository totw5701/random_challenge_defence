package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CMemberNotFoundException;
import com.random.random_challenge_defence.api.dto.member.MemberSignUpReqDto;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public Member join(MemberSignUpReqDto form) {
        return memberRepository.save(
                Member.builder()
                        .joinDate(new SimpleDateFormat("yyyyMMdd").format(new Date()))
                        .modifyDate(new SimpleDateFormat("yyyyMMdd").format(new Date()))
                        .email(form.getEmail())
                        .memberRole(MemberRole.USER)
                        .nickname(form.getNickname())
                        .picture(form.getPicture())
                        .password(form.getPassword())
                        .build());
    }

    public Member findByEmail(String memberEmail) {
        Optional<Member> memberById = memberRepository.findByEmail(memberEmail);
        if(!memberById.isPresent()) {
            throw new CMemberNotFoundException();
        }
        return memberById.get();
    }

}
