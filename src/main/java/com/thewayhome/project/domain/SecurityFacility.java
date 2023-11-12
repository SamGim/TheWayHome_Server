package com.thewayhome.project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "security_facility")
public class SecurityFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // 출입문 보안
    @Column(name = "door_security", columnDefinition = "boolean default false")
    private Boolean doorSecurity;

    // CCTV
    @Column(name = "cctv", columnDefinition = "boolean default false")
    private Boolean cctv;

    // 현관보안
    @Column(name = "entrance_security", columnDefinition = "boolean default false")
    private Boolean entranceSecurity;

    // 경비원
    @Column(name = "security_guard", columnDefinition = "boolean default false")
    private Boolean securityGuard;

}
