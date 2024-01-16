package com.random.random_challenge_defence.global.config.auth.oauth2;

public interface OAuthConfigProvider {
    String getClientId();
    String getRedirectUri();
    String getClientSecret();
    String getTokenUrl();
    String getUserInfoUrl();
}