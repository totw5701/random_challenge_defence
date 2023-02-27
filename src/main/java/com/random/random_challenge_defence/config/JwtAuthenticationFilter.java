package com.random.random_challenge_defence.config;

import com.random.random_challenge_defence.advice.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        ExceptionCode tokenException = null;

        if (token == null) {
            tokenException = ExceptionCode.TOKEN_IS_NULL;
        } else if (!jwtTokenProvider.validateToken(token)) {
            tokenException = ExceptionCode.TOKEN_VALIDATION_FAIL;
        } else {
            String tokenType = jwtTokenProvider.getClaimValue(token, "type");
            System.out.println(tokenType);
            if ("ATK".equals(tokenType)) {
                // access token인 경우에만 Authentication 객체를 가지고 와서 SecurityContext에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        if (tokenException != null) {
            request.setAttribute("token-exception", tokenException);
        }
        chain.doFilter(request, response);
    }
}
