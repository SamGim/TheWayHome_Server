package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.RealComplex;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexSimpleResponseDto {
    private Long id;
    private String tradeTypeName;
    private String name;
    private String dealPrc;
    private String warrantPrc;
    private Double latitude;
    private Double longitude;

    public static RealComplexSimpleResponseDto fromEntity(RealComplex complex) {

        return RealComplexSimpleResponseDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .tradeTypeName(complex.getTradeTypeName())
                .dealPrc(complex.getDealPrc())
                .warrantPrc(complex.getWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();
    }
}


