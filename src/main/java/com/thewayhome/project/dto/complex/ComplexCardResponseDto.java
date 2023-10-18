package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.Complex;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexCardResponseDto {
    private Long articleNo;
    private String articleName;
    private String dealOrWarrantPrc;
    private String tradeTypeName;
    private String direction;
    private String realtorName;
    private String area1;
    private String area2;
    private String districtName;
    private String cortarName;

    public static ComplexCardResponseDto fromEntity(Complex complex){
        return ComplexCardResponseDto.builder()
                .articleNo(complex.getArticleNo())
                .articleName(complex.getArticleName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .tradeTypeName(complex.getTradeTypeName())
                .direction(complex.getDirection())
                .realtorName(complex.getRealtorName())
                .area1(complex.getArea1())
                .area2(complex.getArea2())
                .districtName(complex.getDistrictName())
                .cortarName(complex.getCortarName())
                .build();
    }
}
