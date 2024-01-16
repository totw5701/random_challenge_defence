package com.random.random_challenge_defence.global.config.auth.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthConfigProvider implements OAuthConfigProvider{

    @Value("${oauth.google.user-info-url}")
    private String userInfoUrl;

    @Value("${oauth.google.token-url}")
    private String tokenUrl;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.redirect-url}")
    private String redirectUri;

    @Value("${oauth.google.client-secret}")
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
