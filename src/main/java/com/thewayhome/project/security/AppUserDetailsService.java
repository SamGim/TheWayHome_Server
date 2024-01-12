package com.thewayhome.project.security;


import com.thewayhome.project.domain.AppUser;
import com.thewayhome.project.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws AuthenticationException {
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "appUserUserDetailsService : can not find email : " + email));
        return new AppUserDetails(
                appUser.getId(),
                appUser.getEmail(),
                appUser.getName(),
                appUser.getRole()
        );
    }
}
