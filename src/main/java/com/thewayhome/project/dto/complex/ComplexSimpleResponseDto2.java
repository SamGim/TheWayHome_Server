package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.Complex;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexSimpleResponseDto2 {
    private Long articleNo;
    private String dealOrWarrantPrc;
    private Double latitude;
    private Double longitude;
    private Integer duration;

    public static ComplexSimpleResponseDto2 fromEntity(Complex complex, Integer duration){
        return ComplexSimpleResponseDto2.builder()
                .articleNo(complex.getArticleNo())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .duration(duration)
                .build();
    }
}
