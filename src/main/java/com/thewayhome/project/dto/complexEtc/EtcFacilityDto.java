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
    private Boolean yard;

    public static EtcFacilityDto fromEntity(EtcFacility etcFacilityDto){
        return EtcFacilityDto.builder()
                .fireAlarm(etcFacilityDto.getFireAlarm())
                .unmannedParcelBox(etcFacilityDto.getUnmannedParcelBox())
                .yard(etcFacilityDto.getYard())
                .build();
    }

    public EtcFacility toEntity() {
        return EtcFacility.builder()
                .fireAlarm(this.fireAlarm)
                .unmannedParcelBox(this.unmannedParcelBox)
                .yard(this.yard)
                .build();
    }
}
