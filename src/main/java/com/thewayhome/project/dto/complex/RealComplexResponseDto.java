package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.RealComplex;
import com.thewayhome.project.dto.image.ComplexImageResponseDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexResponseDto {
    private Long id;
    private String tradeTypeName;
    private String dealOrWarrantPrc;
    private String maintenanceCost;
    private String maintenanceCostIncludings;
    private String structure;
    private String supplyArea;
    private String dedicatedArea;
    private Integer wholeFloor;
    private Integer floor;
    private Boolean parkable;
    private Boolean elevatable;
    private String options;
    private String details;
    private String loan;
    private Boolean petable;
    private ComplexImageResponseDto mainImage;
    private List<ComplexImageResponseDto> roomImages;
    private Double latitude;
    private Double longitude;

    public static RealComplexResponseDto fromEntity(RealComplex complex, ComplexImageResponseDto mainImage, List<ComplexImageResponseDto> roomImages) {

        return RealComplexResponseDto.builder()
                .id(complex.getId())
                .tradeTypeName(complex.getTradeTypeName())
                .dealOrWarrantPrc(complex.getDealOrWarrantPrc())
                .maintenanceCost(complex.getMaintenanceCost())
                .maintenanceCostIncludings(complex.getMaintenanceCostIncludings())
                .structure(complex.getStructure())
                .supplyArea(complex.getSupplyArea())
                .dedicatedArea(complex.getDedicatedArea())
                .wholeFloor(complex.getWholeFloor())
                .floor(complex.getFloor())
                .parkable(complex.getParkable())
                .elevatable(complex.getElevatable())
                .options(complex.getOptions())
                .details(complex.getDetails())
                .loan(complex.getLoan())
                .petable(complex.getPetable())
                .mainImage(mainImage)
                .roomImages(roomImages)
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .build();
    }
}
