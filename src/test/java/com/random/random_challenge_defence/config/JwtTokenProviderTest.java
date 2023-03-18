package com.random.random_challenge_defence.config;

import com.random.random_challenge_defence.api.dto.TokenInfo;
import com.random.random_challenge_defence.config.auth.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class JwtTokenProviderTest {


    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHa");

    @Test
    @DisplayName("generateToken 메서드 테스트")
    void generateTokenTest() {
        // given
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User("user", "", authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        // when
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // then
        assertNotNull(tokenInfo.getAccessToken());
        assertNotNull(tokenInfo.getRefreshToken());
        assertEquals("Bearer", tokenInfo.getGrantType());
    }

    @Test
    @DisplayName("getAuthentication 메서드 테스트")
    void getAuthenticationTest() {
        // given
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User("user", "", authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // when
        Authentication result = jwtTokenProvider.getAuthentication(tokenInfo.getAccessToken());

        // then
        assertEquals(userDetails.getUsername(), result.getName());
        assertEquals(userDetails.getAuthorities().size(), result.getAuthorities().size());
    }

    @Test
    @DisplayName("validateToken 메서드 테스트")
    void validateTokenTest() {
        // given
        String accessToken = jwtTokenProvider.generateToken(
                        new UsernamePasswordAuthenticationToken("user", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))))
                .getAccessToken();

        // when
        boolean result = jwtTokenProvider.validateToken(accessToken);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("resolveToken 메서드 테스트")
    void resolveTokenTest() {
        // given
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        when(mockRequest.getHeader(anyString())).thenReturn("Bearer access_token");

        // when
        String result = jwtTokenProvider.resolveToken(mockRequest);

        // then
        assertEquals("access_token", result);
    }

    @Test
    @DisplayName("getClaimValue 메서드 테스트")
    void getClaimValueTest() {
        // given
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User("user", "", authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // when
        String sub = jwtTokenProvider.getClaimValue(tokenInfo.getAccessToken(), "sub");
        String auth = jwtTokenProvider.getClaimValue(tokenInfo.getAccessToken(), "auth");

        // then
        assertEquals(authentication.getName(), sub);
        assertEquals("ROLE_USER", auth);

    }
}