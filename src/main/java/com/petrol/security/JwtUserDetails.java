package com.petrol.security;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public final class JwtUserDetails implements UserDetails {

    private final String id;
    private final String email;
    private final List<GrantedAuthority> authorities;

    public JwtUserDetails(String id,
                          String email,
                          List<GrantedAuthority> authorities) {

        this.id          = requireNonNull(id);
        this.email       = requireNonNull(email);
        this.authorities = requireNonNull(authorities);
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
