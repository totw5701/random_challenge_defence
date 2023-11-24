package com.random.random_challenge_defence.config.auth.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaverOAuthConfigProvider implements OAuthConfigProvider{

    @Value("${oauth.naver.user-info-url}")
    private String userInfoUrl;

    @Value("${oauth.naver.token-url}")
    private String tokenUrl;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.redirect-url}")
    private String redirectUri;

    @Value("${oauth.naver.client-secret}")
    private String clientSecret;


    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getTokenUrl() {
        return tokenUrl;
    }

    @Override
    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
