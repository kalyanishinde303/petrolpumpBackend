package com.petrol.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class JwtTokenAuthenticationProcessingFilter
extends AbstractAuthenticationProcessingFilter {

    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    
    public JwtTokenAuthenticationProcessingFilter(
        AuthenticationFailureHandler failureHandler,
        TokenExtractor tokenExtractor,
        RequestMatcher matcher) {

        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {

        final String tokenPayload = request.getHeader(JWT_TOKEN_HEADER_PARAM);
        final JwtToken token = new JwtToken(tokenExtractor.extract(tokenPayload));

        return getAuthenticationManager().authenticate(
            new JwtAuthentication(token, null)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult
    ) throws IOException, ServletException {

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed
    ) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
