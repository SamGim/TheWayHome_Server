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
    private String name;
    private String tradeTypeName;
    private String dealOrWarrantPrc;
//    private String maintenanceCost;
    //    private String maintenanceCostIncludings;
//    private String structure;
//    private String supplyArea;
//    private String dedicatedArea;
    //    private Integer wholeFloor;
//    private Integer floor;
//    private Boolean parkable;
//    private Boolean elevatable;
//    private String options;
//    private String details;
//    private String loan;
//    private Boolean petable;
//    private String mainImageUrl;
    //    private List<ComplexImageResponseDto> roomImages;
    private Double latitude;
    private Double longitude;

    public static RealComplexSimpleResponseDto fromEntity(RealComplex complex) {

        return RealComplexSimpleResponseDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .tradeTypeName(complex.getTradeTypeName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();
    }
}


