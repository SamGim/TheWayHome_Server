package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.CoolingFacility;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoolingFacilityDto{
    // 벽걸이형
    private Boolean wallMounted;

    // 스탠드형
    private Boolean stand;

    // 천장형
    private Boolean ceiling;

    // 이동형
    private Boolean mobile;

    // 기타
    private String etc;

    public static CoolingFacilityDto fromEntity(CoolingFacility coolingFacility){
        return CoolingFacilityDto.builder()
                .wallMounted(coolingFacility.getWallMounted())
                .stand(coolingFacility.getStand())
                .ceiling(coolingFacility.getCeiling())
                .mobile(coolingFacility.getMobile())
                .etc(coolingFacility.getEtc())
                .build();
    }

    public CoolingFacility toEntity() {
        return CoolingFacility.builder()
                .wallMounted(this.wallMounted)
                .stand(this.stand)
                .ceiling(this.ceiling)
                .mobile(this.mobile)
                .etc(this.etc)
                .build();
    }
}