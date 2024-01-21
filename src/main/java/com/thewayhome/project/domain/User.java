package com.thewayhome.project.domain;

import jakarta.persistence.*;
import lombok.*;



// 이거 바꾸면 UserDetailsServiceImpl 빌더 바꿔 주기
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저 지정 이름
    @Column(name = "nickname", unique = true)
    private String nickname;

    // sns 아이디
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "refresh_token")
    private String refreshToken;

//    @OneToMany(mappedBy = "user")
//    List<Routine> routines = new ArrayList<>();
}
/*
id -> auto

provider
username
--------->bytoken

nickname
isnewbie
gender
height
weight
bodyfatper
muscleper




 */

