package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.CoolingFacility;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoolingFacilityDto{
    Boolean wallMounted;
    Boolean stand;
    Boolean windowMounted;
    Boolean ceiling;
    Boolean separation;
    Boolean centralAirConditioning;

    public static CoolingFacilityDto fromEntity(CoolingFacility coolingFacility){
        return CoolingFacilityDto.builder()
                .wallMounted(coolingFacility.getWallMounted())
                .stand(coolingFacility.getStand())
                .windowMounted(coolingFacility.getWindowMounted())
                .ceiling(coolingFacility.getCeiling())
                .separation(coolingFacility.getSeparation())
                .centralAirConditioning(coolingFacility.getCentralAirConditioning())
                .build();
    }

    public CoolingFacility toEntity() {
        return CoolingFacility.builder()
                .wallMounted(this.wallMounted)
                .stand(this.stand)
                .windowMounted(this.windowMounted)
                .ceiling(this.ceiling)
                .separation(this.separation)
                .centralAirConditioning(this.centralAirConditioning)
                .build();
    }
}