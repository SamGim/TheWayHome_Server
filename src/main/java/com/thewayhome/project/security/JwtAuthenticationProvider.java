package com.thewayhome.project.security;


import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.UserDetailsService;
import com.thewayhome.project.utils.JwtTokenUtils;
import com.thewayhome.project.utils.OauthInfo;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken beforeToken = (JwtAuthenticationToken) authentication;
        String jwtToken = beforeToken.getCredential();

        try {
            if (!jwtTokenUtils.validate(jwtToken)){
                throw  new BadCredentialsException("token not valid");
            }
            OauthInfo oauthInfo = jwtTokenUtils.getOauthInfo(jwtToken);
            String userId = oauthInfo.getUserId();
            String provider = oauthInfo.getProvider();
            UserDetails user = userDetailsService.findUser(userId, provider);
            if (!userDetailsService.isMember(user)){
                throw new CustomException(CustomError.NOT_A_MEMBER);
            }
            return new JwtAuthenticationToken(user, user.getAuthorities());
        } catch (ExpiredJwtException e){
            log.error("token expired");
            throw new AuthenticationException("token expired") {
            };
        } catch (CustomException e) {
            log.error("not a member");
            throw new AuthenticationException("NOT_A_MEMBER") {
            };
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}



