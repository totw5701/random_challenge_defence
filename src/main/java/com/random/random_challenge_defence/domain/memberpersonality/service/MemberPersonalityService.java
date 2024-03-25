package com.random.random_challenge_defence.domain.memberpersonality.service;

import com.random.random_challenge_defence.domain.member.service.MemberService;
import com.random.random_challenge_defence.domain.memberpersonality.dto.MemberPersonalityAssignDto;
import com.random.random_challenge_defence.domain.memberpersonality.dto.MemberPersonalityDetailDto;
import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.member.entity.Member;
import com.random.random_challenge_defence.domain.membermemberpersonality.entity.MemberMemberPersonality;
import com.random.random_challenge_defence.domain.membermemberpersonality.repository.MemberMemberPersonalityRepository;
import com.random.random_challenge_defence.domain.memberpersonality.entity.MemberPersonality;
import com.random.random_challenge_defence.domain.memberpersonality.repository.MemberPersonalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberPersonalityService {

    private final MemberService memberService;

    private final MemberPersonalityRepository memberPersonalityRepository;
    private final MemberMemberPersonalityRepository memberMemberPersonalityRepository;

    public MemberPersonality getEntityById(Long id) {
        Optional<MemberPersonality> byId = memberPersonalityRepository.findById(id);
        if(!byId.isPresent()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_CHALLENGE_CATEGORY);
        }
        return byId.get();
    }

    public List<Long> getMemberPersonalities() {
        Member loginMember = memberService.getLoginMember();
        List<MemberMemberPersonality> memberMemberPersonalities = loginMember.getMemberMemberPersonalities();
        return memberMemberPersonalities.stream().map(e -> e.getMemberPersonality().getId()).collect(Collectors.toList());
    }

    public void assignPersonalities(MemberPersonalityAssignDto form) {
        String memberEmail = memberService.getLoginUserEmail();
        Member loginMember = memberService.getEntityById(memberEmail);

        updatePersonalities(loginMember, form.getIds());
    }

    private void updatePersonalities(Member loginMember, List<Long> ids) {

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

    private void deleteAllPersonalities(Member loginMember) {
        List<MemberMemberPersonality> memberMemberPersonalities = memberMemberPersonalityRepository.findAllByMemberId(loginMember.getId());
        List<Long> memberMemberPersonalityIds = memberMemberPersonalities.stream().map(MemberMemberPersonality::getId).collect(Collectors.toList());
        memberMemberPersonalityRepository.deleteAllById(memberMemberPersonalityIds);
    }

    public List<MemberPersonalityDetailDto> getPersonalities() {
        List<MemberPersonality> all = memberPersonalityRepository.findAll();
        return all.stream().map(MemberPersonality::toDetailDto).collect(Collectors.toList());
    }
}
