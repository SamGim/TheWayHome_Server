package com.thewayhome.project.service;


import com.thewayhome.project.domain.User;
import com.thewayhome.project.repository.UserRepository;
import com.thewayhome.project.security.UserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public UserDetails findUser(String username, String provider) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsernameAndProvider(username, provider)
                .orElseThrow(() -> new UsernameNotFoundException("cannot find such user"));

        return UserDetails.builder()
                .username(username)
                .provider(provider)
                .isEnabled(true)
                .isExpired(false)
                .isLocked(false)
                .build();
    }


    public Boolean isMember(UserDetails user) {
        User member = userRepository
                .findByUsernameAndProvider(user.getUsername(), user.getProvider())
                .orElseThrow(() -> new UsernameNotFoundException("cannot find such user : " + user.getUsername() + " " + user.getProvider()));
        return member.getPhoneNumber() != null && member.getNickname() != null;
    }
}
