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
public class RealComplex extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // 매물 이름
    @Column(name = "name", nullable = false)
    private String name;

    // 거래유형(전세, 월세, 매매)
    @Column(name = "trade_type_name", nullable = false)
    private String tradeTypeName;

    // 주소
    @Column(name = "address", nullable = false)
    private String address;

    // 상세주소
    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    // 공급면적(단위 m^2)
    @Column(name = "supply_area", nullable = false)
    private String supplyArea;

    // 전용면적(단위 m^2)
    @Column(name = "dedicated_area", nullable = false)
    private String dedicatedArea;

    // 구조(오픈형 원룸, 분리형 원룸, 복층형 원룸, 투룸, 쓰리룸, 포룸+)
    @Column(name = "structure", nullable = false)
    private String structure;

    // 방 상세구조
    @Column(name = "detail_structure", nullable = false)
    private String detailStructure;

    // 임대료(전세가, 월세가, 매매가)
    @Column(name = "deal_prc")
    private String dealPrc;

    // 월세 보증금
    @Column(name = "warrant_prc")
    private String warrantPrc;

    // 관리비(단위: 만원)
    @Column(name = "maintenance_fee")
    private String maintenanceCost;

    // 관리비 포함내역(전기세, 가스, 인터넷, 수도, TV)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "maintenance_cost_includings_id", nullable = false)
    private MaintenanceCostIncludings maintenanceCostIncludings;

    // 입주가능일
    @Column(name = "available_date", nullable = false)
    private String availableDate;

    // 입주협가능여부 ---
    @Column(name = "available_date_negotiable", nullable = false)
    private Boolean availableDateNegotiable;

    // 전체층
    @Column(name = "whole_floor", nullable = false)
    private Integer wholeFloor;

    // 해당층
    @Column(name = "floor", nullable = false)
    private Integer floor;

    //---
    // 옥탑형
    @Column(name = "rooftop", nullable = false)
    private Boolean rooftop;

    // 반지하
    @Column(name = "basement", nullable = false)
    private Boolean basement;

    // 지하
    @Column(name = "underground", nullable = false)
    private Boolean underground;
    //---

    // 대표사진
    @Column(name = "main_image_url", nullable = true)
    private String mainImageUrl;

    // 방 사진들
    @OneToMany(mappedBy = "realComplex", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "room_images", nullable = true)
    private List<ComplexImage> roomImages;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "location", columnDefinition="POINT")
    private Point location;

    // 주실 방향 기준
    @Column(name = "room_direction", nullable = false)
    private String roomDirection;

    // 주실 방향
    @Column(name = "room_direction_detail", nullable = false)
    private String roomDirectionDetail;

    // 욕실수
    @Column(name = "bathroom_count", nullable = false)
    private Integer bathroomCount;

    // 주차가능
    @Column(name = "parkable", nullable = false)
    private Integer parkable;

    // 엘리베이터 여부
    @Column(name = "elevatable", nullable = false)
    private Boolean elevatable;

    // 펫 여부
    @Column(name = "petable", nullable = false)
    private Boolean petable;

    // 대출 여부(LH전세대출 가능, 카카오뱅크 전세대출 가능)
    @Column(name = "loan")
    private String loan;


    // 여기서부터 옵션


    // 시설정보옵션들
    // 난방시설
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "heating_facility_id")
    private HeatingFacility heatingFacility;

    // 냉방시설
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cooling_facility_id")
    private CoolingFacility coolingFacility;

    // 생활시설
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "living_facility_id")
    private LivingFacility livingFacility;

    // 보안시설
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "security_facility_id")
    private SecurityFacility securityFacility;

    // 기타시설
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "etc_facility_id")
    private EtcFacility etcFacility;

    // 상세설명
    @Column(name = "description")
    private String description;
}
