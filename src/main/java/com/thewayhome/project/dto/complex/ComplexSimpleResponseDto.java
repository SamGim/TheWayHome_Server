package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.Complex;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexSimpleResponseDto {
    private Long articleNo;
    private String articleName;
    private String dealOrWarrantPrc;
    private Double latitude;
    private Double longitude;

    public static ComplexSimpleResponseDto fromEntity(Complex complex){
        return ComplexSimpleResponseDto.builder()
                .articleNo(complex.getArticleNo())
                .articleName(complex.getArticleName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();
    }
}
