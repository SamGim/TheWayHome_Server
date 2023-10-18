package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.domain.RealComplex;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexSimpleResponseDto2 {
    private Long id;
    private String name;
    private String tradeTypeName;
    private String dealOrWarrantPrc;
    private Double latitude;
    private Double longitude;
    private Integer duration;

    public static RealComplexSimpleResponseDto2 fromEntity(RealComplex complex, Integer duration){
        return RealComplexSimpleResponseDto2.builder()
                .id(complex.getId())
                .name(complex.getName())
                .tradeTypeName(complex.getTradeTypeName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .duration(duration)
                .build();
    }
}