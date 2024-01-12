package com.thewayhome.project.domain;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    // 고객 앱 유저
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    // PK

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = false)
    private CustomRole role;
    // 계정 접근 권한

    @Column(name = "name", nullable = false, unique = false)
    private String name;
    // 이름

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    // 이메일

    @Column(name = "refresh_token", nullable = false, unique = false)
    private String refreshToken;
    // 리프레시 토큰
}
