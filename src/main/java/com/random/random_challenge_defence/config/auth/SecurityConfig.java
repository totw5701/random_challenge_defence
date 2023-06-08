package com.random.random_challenge_defence.config.auth;

import com.random.random_challenge_defence.config.auth.oauth2.CustomOAuth2FailureHandler;
import com.random.random_challenge_defence.config.auth.oauth2.CustomOAuth2SuccessHandler;
import com.random.random_challenge_defence.config.auth.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2FailureHandler customOAuth2FailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    //.antMatchers( "/auth/login", "/auth/all", "/auth/token-reissue", "/auth/login-fail", "/members/join", "/h2-console", "/h2-console/*", "/admin/**/*").permitAll()
                    .antMatchers("/auth/user").hasRole("USER")
                    .antMatchers("/user-only").hasRole("USER")
                    .antMatchers("/admin-only").hasRole("ADMIN")
                    .anyRequest().permitAll()
                    //.anyRequest().authenticated()
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
            .and()
                .successHandler(customOAuth2SuccessHandler)
                .failureHandler(customOAuth2FailureHandler)

            .and()
                //.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())

        ;
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException,
                ServletException {
            // 권한 없음
            System.out.println(exception.toString());
            response.sendRedirect("/auth/access-denied");
        }
    }

    /**
     * 로그인 정보가 없음.
     */
    public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,
                ServletException {
            System.out.println(exception.toString());
            response.sendRedirect("/auth/login-fail");
        }
    }
}
