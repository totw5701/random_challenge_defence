package com.random.random_challenge_defence.config.auth.oauth2;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthConfigProviderFactory {

    private final GoogleOAuthConfigProvider googleOAuthConfigProvider;
    private final NaverOAuthConfigProvider naverOAuthConfigProvider;
    private final KakaoOAuthConfigProvider kakaoOAuthConfigProvider;

    public OAuthConfigProvider getProvider(String social) {
        switch (social) {
            case "google":
                return googleOAuthConfigProvider;
            case "naver":
                return naverOAuthConfigProvider;
            case "kakao":
                return kakaoOAuthConfigProvider;
            default:
                throw new CustomException(ExceptionCode.UNSUPPORTED_SOCIAL_LOGIN);
        }
    }
}