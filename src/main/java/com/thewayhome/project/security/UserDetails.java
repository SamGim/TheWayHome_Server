package com.thewayhome.project.security;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@Builder
public class UserDetails {

        private String provider;

        private String username;

        private Collection<? extends GrantedAuthority> authorities;

        private boolean isLocked;

        private boolean isEnabled;

        private boolean isExpired;

}
