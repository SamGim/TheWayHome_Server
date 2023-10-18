package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.Complex;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexDetailResponseDto {
    private Long articleNo;
    private String articleName;
    private String dealOrWarrantPrc;
    private String tradeTypeName;
    private String direction;
    private String cpName;
    private String realtorName;
    private Double latitude;
    private Double longitude;
    private String area1;
    private String area2;
    private String districtName;
    private String cortarName;
    private String cortarNumber;
    private Long complexNo;
    private String complexName;

    public static ComplexDetailResponseDto fromEntity(Complex complex){
        return ComplexDetailResponseDto.builder()
                .articleNo(complex.getArticleNo())
                .articleName(complex.getArticleName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .tradeTypeName(complex.getTradeTypeName())
                .direction(complex.getDirection())
                .cpName(complex.getCpName())
                .realtorName(complex.getRealtorName())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .area1(complex.getArea1())
                .area2(complex.getArea2())
                .districtName(complex.getDistrictName())
                .cortarName(complex.getCortarName())
                .cortarNumber(complex.getCortarNumber())
                .complexNo(complex.getComplexNo())
                .complexName(complex.getComplexName())
                .build();
    }
}
