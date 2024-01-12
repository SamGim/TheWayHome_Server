package com.thewayhome.project.security;


import com.thewayhome.project.repository.AppUserRepository;
import com.thewayhome.project.utils.JwtTokenUtils;
import com.thewayhome.project.utils.OauthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtTokenProvider implements AuthenticationProvider {

    @Autowired
    public JwtTokenProvider(AppUserDetailsService appUserDetailsService, AppUserRepository appUserRepository, JwtTokenUtils jwtTokenUtils) {
        this.appUserDetailsService = appUserDetailsService;
        this.appUserRepository = appUserRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }


    final AppUserDetailsService appUserDetailsService;
    final AppUserRepository appUserRepository;
    final JwtTokenUtils jwtTokenUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwtToken = authentication.getCredentials().toString();

        // JwtToken을 검증하고 사용자 정보를 가져오는 로직
        try {
            if (!jwtTokenUtils.validate(jwtToken)){
                throw new BadCredentialsException("token not valid");
            }
            OauthInfo oauthInfo = jwtTokenUtils.getOauthInfo(jwtToken);
//            String iss = oauthInfo.getIss();

            String email = oauthInfo.getSub();
            AppUserDetails user = (AppUserDetails) appUserDetailsService.loadUserByUsername(email);
            if (user == null) {
                throw new BadCredentialsException("CustomerUserDetails is null");
            }
            return new JwtAuthenticationToken(user, user.getAuthorities());
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
            throw new BadCredentialsException(e.getLocalizedMessage());
        }


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}


