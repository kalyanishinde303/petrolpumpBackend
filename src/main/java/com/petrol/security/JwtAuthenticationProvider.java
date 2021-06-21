package com.petrol.security;


import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;


@Component
public final class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwt;

    public JwtAuthenticationProvider(JwtService jwt) {
        this.jwt = requireNonNull(jwt);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
    throws AuthenticationException {
        final JwtToken token = (JwtToken) authentication.getCredentials();
        final Claims claims = jwt.validate(token);

        final String userId = claims.getSubject();
        final String email  = claims.get("mail", String.class);

        @SuppressWarnings("unchecked")
        final List<String> scopes = (List<String>) claims.get("scopes", List.class);
        final List<GrantedAuthority> auths = scopes.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        final JwtUserDetails user = new JwtUserDetails(userId, email, auths);
        return new JwtAuthentication(token, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthentication.class.isAssignableFrom(authentication));
    }
}
