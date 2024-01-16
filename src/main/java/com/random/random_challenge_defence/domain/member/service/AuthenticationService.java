package com.random.random_challenge_defence.domain.member.service;

import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.global.advice.exception.StackTraceCustomException;
import com.random.random_challenge_defence.domain.member.dto.TokenInfo;
import com.random.random_challenge_defence.global.config.auth.JwtTokenProvider;
import com.random.random_challenge_defence.global.config.auth.oauth2.OAuthAttributes;
import com.random.random_challenge_defence.global.config.auth.oauth2.OAuthConfigProvider;
import com.random.random_challenge_defence.global.config.auth.oauth2.OAuthConfigProviderFactory;
import com.random.random_challenge_defence.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuthConfigProviderFactory oAuthConfigProviderFactory;

    private final RestTemplate restTemplate = new RestTemplate();

    public OAuthAttributes getOAuthAttribute(String social, String code) {
        // 인가 코드를 사용하여 accessToken 조회
        Map<String, Object> token = getAccessToken(social, code);

        // accessToken을 사용하여 사용자 정보 조회
        Map<String, Object> attributes = getUserInfo(social, (String) token.get("access_token"));

        // 사용자 정보를 이용하여 oAuthAttribute 객체 생성
        return OAuthAttributes.of(social, attributes);
    }

    public TokenInfo generateTokenInfo(Member member) {
        String authority = member.getMemberRole().getKey();
        return jwtTokenProvider.generateTokenWithAuthAndEmail(authority, member.getEmail());
    }


    public String resolveEmail(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if(token == null) {
            throw new CustomException(ExceptionCode.TOKEN_IS_NULL);
        }
        return jwtTokenProvider.getClaimValue(token, "email");
    }

    private Map<String, Object> getAccessToken(String social, String code) {

        String authorizeCode = null;
        try {
            authorizeCode = URLDecoder.decode(code, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new StackTraceCustomException(ExceptionCode.UNKNOWN_EXCEPTION, e);
        }

        OAuthConfigProvider provider = oAuthConfigProviderFactory.getProvider(social);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", provider.getClientId());
        params.add("client_secret", provider.getClientSecret());
        params.add("redirect_uri", provider.getRedirectUri());
        params.add("code", authorizeCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(provider.getTokenUrl(), request, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Failed to retrieve user information: HTTP status {}", response.getStatusCode());
                throw new CustomException(ExceptionCode.SOCIAL_LOGIN_ERROR);
            }
        } catch (HttpClientErrorException e) {
            log.error("An error occurred while trying to retrieve the access token: {}", e.getMessage());
            throw new StackTraceCustomException(ExceptionCode.UNKNOWN_EXCEPTION, e);
        }
    }

    private Map<String, Object> getUserInfo(String social, String accessToken) {
        OAuthConfigProvider provider = oAuthConfigProviderFactory.getProvider(social);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    provider.getUserInfoUrl(),
                    HttpMethod.GET,
                    requestEntity,
                    Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Failed to retrieve user information: HTTP status {}", response.getStatusCode());
                throw new CustomException(ExceptionCode.SOCIAL_LOGIN_ERROR);
            }
        } catch (HttpClientErrorException e) {
            log.error("An error occurred while trying to retrieve the user information: {}", e.getMessage());
            throw new StackTraceCustomException(ExceptionCode.UNKNOWN_EXCEPTION, e);
        }
    }
}
