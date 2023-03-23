package com.random.random_challenge_defence.testtool;


import com.random.random_challenge_defence.domain.challenge.Challenge;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRole;

public class TestTools {

    public Member createDummyMember(MemberRole role, Long id, String email) {

        Member member = Member.builder()
                .id(id)
                .email(email)
                .password("dummyPwd")
                .memberRole(role)
                .nickname("dummyNickname")
                .picture("")
                .build();
        return member;
    }

}
