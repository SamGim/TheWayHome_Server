package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.MaintenanceCostIncludings;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceCostIncludingsDto {
    Boolean electricity;
    Boolean gas;
    Boolean internet;
    Boolean water;
    Boolean tv;

    public static MaintenanceCostIncludingsDto fromEntity(MaintenanceCostIncludings maintenanceCostIncludings){
        return MaintenanceCostIncludingsDto.builder()
                .electricity(maintenanceCostIncludings.getElectricity())
                .gas(maintenanceCostIncludings.getGas())
                .internet(maintenanceCostIncludings.getInternet())
                .water(maintenanceCostIncludings.getWater())
                .tv(maintenanceCostIncludings.getTv())
                .build();
    }

    // toEntity
    public MaintenanceCostIncludings toEntity(){
        return MaintenanceCostIncludings.builder()
                .electricity(this.electricity)
                .gas(this.gas)
                .internet(this.internet)
                .water(this.water)
                .tv(this.tv)
                .build();
    }

}
