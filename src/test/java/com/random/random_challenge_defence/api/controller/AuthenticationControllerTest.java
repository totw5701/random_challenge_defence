package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.TokenInfo;
import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.member.MemberLoginReqDto;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRole;
import com.random.random_challenge_defence.api.service.AuthenticationService;
import com.random.random_challenge_defence.api.service.MemberService;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.testtool.TestTools;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private MemberService memberService;

    @Mock
    private ResponseService responseService;

    TestTools testTools = new TestTools();

    @Test
    @DisplayName("Token Reissue Test")
    public void tokenReissueTest() {
        // Given
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String token = "old_RTK";
        Member member = testTools.createDummyMember(MemberRole.USER, 1L, "test_id");
        TokenInfo tokenInfo = TokenInfo.builder().accessToken("new_token").refreshToken("refresh_token").build();
        CommonResponse response = CommonResponse.builder().success(true).data(tokenInfo).msg("성공").code("0").build();

        when(authenticationService.resolveToken(request)).thenReturn(token);
        when(authenticationService.resolveSubject(token)).thenReturn(member.getEmail());
        when(memberService.findByEmail(member.getEmail())).thenReturn(member);
        when(authenticationService.login(member.getEmail(), member.getPassword())).thenReturn(tokenInfo);
        when(responseService.getResult(ArgumentMatchers.any())).thenReturn(response);

        // When
        CommonResponse<TokenInfo> result = authenticationController.tokenReissue(request);

        // Then
        assertEquals(result.getData(), tokenInfo);
    }

    @Test
    @DisplayName("Login Test")
    public void loginTest() {

        // Given
        MemberLoginReqDto form = new MemberLoginReqDto("test_id", "test_password");
        TokenInfo tokenInfo = TokenInfo.builder().accessToken("new_token").refreshToken("refresh_token").build();
        CommonResponse response = CommonResponse.builder().success(true).data(tokenInfo).msg("성공").code("0").build();

        when(authenticationService.login(form.getEmail(), form.getPassword())).thenReturn(tokenInfo);
        when(responseService.getResult(ArgumentMatchers.any())).thenReturn(response);

        // When
        CommonResponse<TokenInfo> result = authenticationController.login(form);

        // Then
        assertEquals(result.getData(), tokenInfo);
    }
}