package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.EtcFacility;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtcFacilityDto {
    private Boolean fireAlarm;
    private Boolean unmannedParcelBox;
    private Boolean veranda;
    private Boolean terrace;
    private Boolean fireExtinguisher;
    private String etc;

    public static EtcFacilityDto fromEntity(EtcFacility etcFacilityDto){
        return EtcFacilityDto.builder()
                .fireAlarm(etcFacilityDto.getFireAlarm())
                .unmannedParcelBox(etcFacilityDto.getUnmannedParcelBox())
                .veranda(etcFacilityDto.getVeranda())
                .terrace(etcFacilityDto.getTerrace())
                .fireExtinguisher(etcFacilityDto.getFireExtinguisher())
                .etc(etcFacilityDto.getEtc())
                .build();
    }

    public EtcFacility toEntity() {
        return EtcFacility.builder()
                .fireAlarm(this.fireAlarm)
                .unmannedParcelBox(this.unmannedParcelBox)
                .veranda(this.veranda)
                .terrace(this.terrace)
                .fireExtinguisher(this.fireExtinguisher)
                .etc(this.etc)
                .build();
    }
}
