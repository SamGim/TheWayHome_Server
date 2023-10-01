package com.thewayhome.project.dto.complex;

import com.thewayhome.project.controller.ComplexController;
import com.thewayhome.project.domain.Complex;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexSimpleRequestDto2 {
    private Long articleNo;
    private String dealOrWarrantPrc;
    private Double latitude;
    private Double longitude;
    private Integer duration;

    public static ComplexSimpleRequestDto2 fromEntity(Complex complex, Integer duration){
        return ComplexSimpleRequestDto2.builder()
                .articleNo(complex.getArticleNo())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .duration(duration)
                .build();
    }
}
