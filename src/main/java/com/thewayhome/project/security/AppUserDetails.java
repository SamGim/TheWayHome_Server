package com.thewayhome.project.security;


import com.thewayhome.project.domain.CustomRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
//@Builder
public class AppUserDetails implements UserDetails {

    // PK값
    private Long id;

//    //고객이름
    private String name;

    //기기 시리얼 번호
    private String email;

//    //고객 전화번호
//    private String phoneNumber;

    //권한
    private CustomRole role;

    // 사용자의 권한 목록
    private Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(Long id, String email, String name, CustomRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getValue()));
        this.authorities = authorities;
    }

    /*
     *  권한목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /*
     *
     */
    @Override
    public String getPassword() {
        return null;
    }

    /*
     *
     */
    @Override
    public String getUsername() {
        return null;
    }

    /*
     * 만료 여부
     * true : 만료안됨
     * false : 만료됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /*
     * 계정 잠김 여부
     * true : 안잠김
     * false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * 계정 활성화 여부
     * true : 만료 안됨
     * false : 만료됨
     */
    @Override
    public boolean isEnabled() {
        return (isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired());
    }
}
