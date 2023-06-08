package com.random.random_challenge_defence.config.auth.oauth2;

import com.random.random_challenge_defence.domain.member.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CustomOAuth2User extends DefaultOAuth2User {

    private Member member;
    private String registrationId;

    public CustomOAuth2User(Set<SimpleGrantedAuthority> singleton, Map<String, Object> attributes, String nameAttributeKey) {
        super(singleton, attributes, nameAttributeKey);
    }
}
