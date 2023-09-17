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
public class ComplexSimpleRequestDto {
    private Long articleNo;
    private String articleName;
    private String dealOrWarrantPrc;
    private Double latitude;
    private Double longitude;

    public static ComplexSimpleRequestDto fromEntity(Complex complex){
        return ComplexSimpleRequestDto.builder()
                .articleNo(complex.getArticleNo())
                .articleName(complex.getArticleName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();
    }
}
