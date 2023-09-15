package com.thewayhome.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "complex")
public class Complex {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "article_no")
    private Long articleNo;
    @Column(name = "article_name")
    private String articleName;
    @Column(name = "deal_or_warrant_prc")
    private String dealOrWarrantPrc;
    @Column(name = "trade_type_name")
    private String tradeTypeName;
    @Column(name = "direction")
    private String direction;
    @Column(name = "cp_name")
    private String cpName;
    @Column(name = "realtor_name")
    private String realtorName;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "location", columnDefinition="POINT")
    private Point location;
    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "area1")
    private String area1;
    @Column(name = "area2")
    private String area2;
    @Column(name = "district_name")
    private String districtName;
    @Column(name = "cortar_name")
    private String cortarName;
    @Column(name = "cortar_number")
    private String cortarNumber;
    @Column(name = "complex_no")
    private Long complexNo;
    @Column(name = "complex_name")
    private String complexName;

}
