package com.thewayhome.project.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "real_complex")
public class RealComplex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // 거래유형(전세, 월세, 매매)
    @Column(name = "trade_type_name")
    private String tradeTypeName;

    // 임대료 또는 보증금(단위: 만원, ex: "1억 2,000", "23억 200", 1,500)
    @Column(name = "deal_or_warrant_prc")
    private String dealOrWarrantPrc;

    // 관리비(단위: 만원)
    @Column(name = "maintenance_fee")
    private String maintenanceCost;

    // 관리비 포함내역(전기세, 가스, 인터넷, 수도, TV)
    @Column(name = "maintenance_fee_includings")
    private String maintenanceCostIncludings;

    // 구조(오픈형 원룸, 분리형 원룸, 복층형 원룸, 투룸, 쓰리룸, 포룸+)
    @Column(name = "structure")
    private String structure;

    // 공급면적(단위 m^2)
    @Column(name = "supply_area")
    private String supplyArea;

    // 전용면적(단위 m^2)
    @Column(name = "dedicated_area")
    private String dedicatedArea;

    // 전체층
    @Column(name = "whole_floor")
    private Integer wholeFloor;

    // 해당층
    @Column(name = "floor")
    private Integer floor;

    // 주차가능
    @Column(name = "parkable")
    private Boolean parkable;

    // 엘리베이터 여부
    @Column(name = "elevatable")
    private Boolean elevatable;

    // 옵션(싱크대, 에어컨, 세탁기, ...etc)
    @Column(name = "options")
    private String options;

    // 매물 설명
    @Column(name = "details")
    private String details;

    // 대출 여부(LH전세대출 가능, 카카오뱅크 전세대출 가능)
    @Column(name = "loan")
    private String loan;

    // 펫 여부
    @Column(name = "petable")
    private Boolean petable;

    // 대표사진
    @Column(name = "main_image_url")
    private String mainImageUrl;

    // 방 사진들
    @OneToMany(mappedBy = "realComplex", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "room_images")
    private List<ComplexImage> roomImages;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "location", columnDefinition="POINT")
    private Point location;

}
