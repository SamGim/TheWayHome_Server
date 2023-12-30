package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.RealComplex;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexSimpleResponseDto {
    private Long id;
    private String address;
    private String tradeTypeName;
    private String dealPrc;
    private String warrantPrc;
    private String maintenanceCost;
    private String structure;
    private String dedicatedArea;
    private Integer floor;
    private Double latitude;
    private Double longitude;
    private Integer duration;

    public static RealComplexSimpleResponseDto fromEntity(RealComplex complex, Integer duration){
        return RealComplexSimpleResponseDto.builder()
                .id(complex.getId())
                .address(complex.getAddress())
                .tradeTypeName(complex.getTradeTypeName())
                .dealPrc(complex.getDealPrc())
                .warrantPrc(complex.getWarrantPrc())
                .maintenanceCost(complex.getMaintenanceCost())
                .structure(complex.getStructure())
                .dedicatedArea(complex.getDedicatedArea())
                .floor(complex.getFloor())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .duration(duration)
                .build();
    }
}