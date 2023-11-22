package com.thewayhome.project.dto.complexEtc;


import com.thewayhome.project.domain.HeatingFacility;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatingFacilityDto {

    private Boolean centralHeating;

    private Boolean individualHeating;

    private Boolean regionalHeating;

    public static HeatingFacilityDto fromEntity(HeatingFacility heatingFacility){
        return HeatingFacilityDto.builder()
                .centralHeating(heatingFacility.getCentralHeating())
                .individualHeating(heatingFacility.getIndividualHeating())
                .regionalHeating(heatingFacility.getRegionalHeating())
                .build();
    }

    public HeatingFacility toEntity() {
        return HeatingFacility.builder()
                .centralHeating(this.centralHeating)
                .individualHeating(this.individualHeating)
                .regionalHeating(this.regionalHeating)
                .build();
    }

}
