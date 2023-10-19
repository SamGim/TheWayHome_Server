package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.RealComplex;
import jakarta.persistence.Column;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexRegisterRequestDto {
    private String name;
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
    private Double latitude;
    private Double longitude;

    public RealComplex toEntity() throws ParseException {
        Point point = (latitude != null && longitude != null)
                ? (Point) new WKTReader().read(String.format("POINT(%s %s)", latitude, longitude))
                : null;
        return RealComplex.builder()
                .name(this.name)
                .tradeTypeName(this.tradeTypeName)
                .dealOrWarrantPrc(this.dealOrWarrantPrc)
                .maintenanceCost(this.maintenanceCost)
                .maintenanceCostIncludings(this.maintenanceCostIncludings)
                .structure(this.structure)
                .supplyArea(this.supplyArea)
                .dedicatedArea(this.dedicatedArea)
                .wholeFloor(this.wholeFloor)
                .floor(this.floor)
                .parkable(this.parkable)
                .elevatable(this.elevatable)
                .options(this.options)
                .details(this.details)
                .loan(this.loan)
                .petable(this.petable)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .location(point)
                .build();
    }
}

