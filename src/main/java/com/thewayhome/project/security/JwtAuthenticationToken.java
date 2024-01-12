package com.thewayhome.project.security;


import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final String credential;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.principal = null;
        this.credential = token;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(String provider, String token) {
        super(null);
        this.principal = provider;
        this.credential = token;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
        this.credential = null;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}


