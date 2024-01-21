package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.SecurityFacility;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityFacilityDto {
    // CCTV
    private Boolean cctv;

    // 공동현관보안
    private Boolean entranceSecurity;

    // 경비원
    private Boolean securityGuard;

    // 비디어폰
    private Boolean videoPhone;

    // 인터폰
    private Boolean interPhone;

    // 원룸도어락
    private Boolean oneRoomDoorLock;

    // 창문 방법창
    private Boolean windowMethodWindow;

    // 기타
    private String etc;

    public static SecurityFacilityDto fromEntity(SecurityFacility securityFacility){
        return SecurityFacilityDto.builder()
                .cctv(securityFacility.getCctv())
                .entranceSecurity(securityFacility.getEntranceSecurity())
                .securityGuard(securityFacility.getSecurityGuard())
                .videoPhone(securityFacility.getVideoPhone())
                .interPhone(securityFacility.getInterPhone())
                .oneRoomDoorLock(securityFacility.getOneRoomDoorLock())
                .windowMethodWindow(securityFacility.getWindowMethodWindow())
                .etc(securityFacility.getEtc())
                .build();
    }

    public SecurityFacility toEntity() {
        return SecurityFacility.builder()
                .cctv(this.cctv)
                .entranceSecurity(this.entranceSecurity)
                .securityGuard(this.securityGuard)
                .videoPhone(this.videoPhone)
                .interPhone(this.interPhone)
                .oneRoomDoorLock(this.oneRoomDoorLock)
                .windowMethodWindow(this.windowMethodWindow)
                .etc(this.etc)
                .build();
    }
}
