package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.Complex;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexRegisterRequestDto {
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

    public Complex toEntity() throws ParseException {
        Point point = (latitude != null && longitude != null)
                ? (Point) new WKTReader().read(String.format("POINT(%s %s)", latitude, longitude))
                : null;
        return Complex.builder()
                .articleName(articleName)
                .dealOrWarrantPrc(dealOrWarrantPrc)
                .tradeTypeName(tradeTypeName)
                .direction(direction)
                .cpName(cpName)
                .realtorName(realtorName)
                .latitude(latitude)
                .longitude(longitude)
                .area1(area1)
                .area2(area2)
                .districtName(districtName)
                .cortarName(cortarName)
                .cortarNumber(cortarNumber)
                .complexNo(complexNo)
                .complexName(complexName)
                .location(point)
                .build();
    }
}
