package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.SecurityFacility;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityFacilityDto {
    private Boolean privateSecurityGuard;
    private Boolean cctv;
    private Boolean entranceSecurity;
    private Boolean securityGuard;
    private Boolean videoPhone;
    private Boolean interPhone;
    private Boolean oneRoomDoorLock;
    private Boolean windowMethodWindow;

    public static SecurityFacilityDto fromEntity(SecurityFacility securityFacility){
        return SecurityFacilityDto.builder()
                .privateSecurityGuard(securityFacility.getPrivateSecurityGuard())
                .cctv(securityFacility.getCctv())
                .entranceSecurity(securityFacility.getEntranceSecurity())
                .securityGuard(securityFacility.getSecurityGuard())
                .videoPhone(securityFacility.getVideoPhone())
                .interPhone(securityFacility.getInterPhone())
                .oneRoomDoorLock(securityFacility.getOneRoomDoorLock())
                .windowMethodWindow(securityFacility.getWindowMethodWindow())
                .build();
    }

    public SecurityFacility toEntity() {
        return SecurityFacility.builder()
                .privateSecurityGuard(this.privateSecurityGuard)
                .cctv(this.cctv)
                .entranceSecurity(this.entranceSecurity)
                .securityGuard(this.securityGuard)
                .videoPhone(this.videoPhone)
                .interPhone(this.interPhone)
                .oneRoomDoorLock(this.oneRoomDoorLock)
                .windowMethodWindow(this.windowMethodWindow)
                .build();
    }
}
