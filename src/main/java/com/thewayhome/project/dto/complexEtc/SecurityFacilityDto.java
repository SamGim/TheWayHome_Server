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
    private Boolean doorSecurity;
    private Boolean cctv;
    private Boolean entranceSecurity;
    private Boolean securityGuard;

    public static SecurityFacilityDto fromEntity(SecurityFacility securityFacility){
        return SecurityFacilityDto.builder()
                .doorSecurity(securityFacility.getDoorSecurity())
                .cctv(securityFacility.getCctv())
                .entranceSecurity(securityFacility.getEntranceSecurity())
                .securityGuard(securityFacility.getSecurityGuard())
                .build();
    }

    public SecurityFacility toEntity() {
        return SecurityFacility.builder()
                .doorSecurity(this.doorSecurity)
                .cctv(this.cctv)
                .entranceSecurity(this.entranceSecurity)
                .securityGuard(this.securityGuard)
                .build();
    }
}
