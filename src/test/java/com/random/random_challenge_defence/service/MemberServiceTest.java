package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.advice.exception.CMemberNotFoundException;
import com.random.random_challenge_defence.api.dto.member.MemberPutReqDto;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRepository;
import com.random.random_challenge_defence.domain.member.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @Transactional
    public void joinTest() throws Exception {
        // given
        MemberPutReqDto form = MemberPutReqDto.builder()
                .email("test@test.com")
                .nickname("test")
                .password("password")
                .picture("test.jpg")
                .build();

        Member member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("password")
                .picture("test.jpg")
                .memberRole(MemberRole.USER)
                .joinDate(new SimpleDateFormat("yyyyMMdd").format(new Date()))
                .modifyDate(new SimpleDateFormat("yyyyMMdd").format(new Date()))
                .build();

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        Member result = memberService.join(form);

        // then
        Assertions.assertThat(result.getEmail()).isEqualTo(form.getEmail());
        Assertions.assertThat(result.getNickname()).isEqualTo(form.getNickname());
        Assertions.assertThat(result.getPassword()).isEqualTo(form.getPassword());
        Assertions.assertThat(result.getPicture()).isEqualTo(form.getPicture());
        Assertions.assertThat(result.getMemberRole()).isEqualTo(MemberRole.USER);
        Assertions.assertThat(result.getJoinDate()).isEqualTo(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        Assertions.assertThat(result.getModifyDate()).isEqualTo(new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }


    @Test
    public void testFindByEmail() {
        // given
        String email = "test@test.com";
        Member member = Member.builder()
                .email(email)
                .nickname("tester")
                .password("test1234")
                .memberRole(MemberRole.USER)
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        // when
        Member result = memberService.findByEmail(email);

        // then
        verify(memberRepository).findByEmail(anyString());
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Update member successfully")
    void updateMemberSuccess() {
        // given
        MemberPutReqDto updateForm = MemberPutReqDto.builder()
                .id(1L)
                .nickname("updated")
                .picture("picture")
                .password("password")
                .build();

        Member testMember = Member.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .password("password")
                .build();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(testMember));

        // when
        Member updatedMember = memberService.update(updateForm);

        // then
        Assertions.assertThat(updatedMember.getNickname()).isEqualTo(updateForm.getNickname());
        Assertions.assertThat(updatedMember.getPicture()).isEqualTo(updateForm.getPicture());
        Assertions.assertThat(updatedMember.getPassword()).isEqualTo(updateForm.getPassword());
    }

    @Test
    @DisplayName("Update member fails due to member not found")
    void updateMemberFail() {
        // given
        MemberPutReqDto updateForm = MemberPutReqDto.builder()
                .id(2L)
                .email("updated@test.com")
                .nickname("updated")
                .picture("picture")
                .password("password")
                .build();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThatThrownBy(() -> memberService.update(updateForm)).isInstanceOf(CMemberNotFoundException.class);
    }

    @Test
    @DisplayName("Delete member successfully")
    void deleteMemberSuccess() {
        // given

        Member testMember = Member.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .password("password")
                .build();

        Long testMemberId = testMember.getId();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(testMember));

        // when
        memberService.delete(testMemberId);

        // then
        verify(memberRepository, times(1)).delete(any(Member.class));
    }

    @Test
    @DisplayName("Delete member fails due to member not found")
    void deleteMemberFail() {
        // given
        Long testMemberId = 2L;

        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThatThrownBy(() -> memberService.delete(testMemberId)).isInstanceOf(CMemberNotFoundException.class);
    }

}