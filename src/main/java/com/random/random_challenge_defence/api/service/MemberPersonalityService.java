package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.membermemberpersonality.MemberMemberPersonality;
import com.random.random_challenge_defence.domain.membermemberpersonality.MemberMemberPersonalityRepository;
import com.random.random_challenge_defence.domain.memberpersonality.MemberPersonality;
import com.random.random_challenge_defence.domain.memberpersonality.MemberPersonalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberPersonalityService {

    private final MemberPersonalityRepository memberPersonalityRepository;
    private final MemberMemberPersonalityRepository memberMemberPersonalityRepository;

    public MemberPersonality readOne(Long id) {
        Optional<MemberPersonality> byId = memberPersonalityRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CATEGORY);
        }
        return byId.get();
    }

    @Transactional
    public void updatePersonalities(Member loginMember, List<Long> ids) {

        deleteAllPersonalities(loginMember);

        for(Long id : ids) {

            Optional<MemberPersonality> byId = memberPersonalityRepository.findById(id);
            if(!byId.isPresent()) {
                throw new CustomException(ExceptionCode.NOT_FOUND_PERSONALITY);
            }

            MemberPersonality memberPersonality = byId.get();

            MemberMemberPersonality memberMemberPersonality = MemberMemberPersonality.builder()
                    .member(loginMember)
                    .memberPersonality(memberPersonality)
                    .build();

            memberMemberPersonalityRepository.save(memberMemberPersonality);
        }
    }

    public List<MemberPersonality> findAll() {
        return memberPersonalityRepository.findAll();
    }

    public void deleteAllPersonalities(Member loginMember) {
        List<MemberMemberPersonality> memberMemberPersonalities = memberMemberPersonalityRepository.findAllByMemberId(loginMember.getId());
        List<Long> memberMemberPersonalityIds = memberMemberPersonalities.stream().map(e -> e.getId()).collect(Collectors.toList());
        memberMemberPersonalityRepository.deleteAllById(memberMemberPersonalityIds);
    }
}
