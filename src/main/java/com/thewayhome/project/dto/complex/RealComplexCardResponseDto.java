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
    private String dealOrWarrantPrc;
    private String maintenanceCost;
//    private String maintenanceCostIncludings;
//    private String structure;
    private String supplyArea;
    private String dedicatedArea;
//    private Integer wholeFloor;
//    private Integer floor;
//    private Boolean parkable;
//    private Boolean elevatable;
//    private String options;
//    private String details;
//    private String loan;
//    private Boolean petable;
    private String mainImageUrl;
//    private List<ComplexImageResponseDto> roomImages;
    private Double latitude;
    private Double longitude;

    public static RealComplexCardResponseDto fromEntity(RealComplex complex) {

        return RealComplexCardResponseDto.builder()
                .id(complex.getId())
                .tradeTypeName(complex.getTradeTypeName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .maintenanceCost(complex.getMaintenanceCost())
                .supplyArea(complex.getSupplyArea())
                .dedicatedArea(complex.getDedicatedArea())
                .mainImageUrl(complex.getMainImageUrl())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();
    }
}
