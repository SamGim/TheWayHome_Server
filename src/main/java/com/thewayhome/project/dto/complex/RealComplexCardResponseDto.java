package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.RealComplex;
import com.thewayhome.project.dto.image.ComplexImageResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexCardResponseDto {
    private Long id;
    private String name;
    private String tradeTypeName;
    private Integer duration;
    private String dealPrc;
    private String warrantPrc;
    private String supplyArea;
    private String dedicatedArea;
    private String mainImageUrl;
    private Double latitude;
    private Double longitude;

    public static RealComplexCardResponseDto fromEntity(RealComplex complex, Integer duration){
        return RealComplexCardResponseDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .tradeTypeName(complex.getTradeTypeName())
                .duration(duration)
                .dealPrc(complex.getDealPrc())
                .warrantPrc(complex.getWarrantPrc())
                .supplyArea(complex.getSupplyArea())
                .dedicatedArea(complex.getDedicatedArea())
                .mainImageUrl(complex.getMainImageUrl())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();

    }
}
