package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.*;
import com.thewayhome.project.dto.complexEtc.*;
import com.thewayhome.project.dto.image.ComplexImageResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexDetailResponseDto {
    // id
    private Long id;

    // 매물 이름
    private String name;

    // 거래유형(전세, 월세, 매매)
    private String tradeTypeName;

    // 주소
    private String address;

    // 상세주소
    private String detailAddress;

    // 공급면적(단위 m^2)
    private String supplyArea;

    // 전용면적(단위 m^2)
    private String dedicatedArea;

    // 구조(오픈형 원룸, 분리형 원룸, 복층형 원룸, 투룸, 쓰리룸, 포룸+)
    private String structure;

    // 방 상세구조
    private String detailStructure;

    // 임대료(전세가, 월세가, 매매가)
    private String dealPrc;

    // 월세 보증금
    private String warrantPrc;

    // 관리비(단위: 만원)
    private String maintenanceCost;

    // 관리비 포함내역(전기세, 가스, 인터넷, 수도, TV)
    private MaintenanceCostIncludingsDto maintenanceCostIncludings;

    // 입주가능일
    private String availableDate;

    // 입주협가능여부 ---
    private Boolean availableDateNegotiable;

    // 전체층
    private Integer wholeFloor;

    // 해당층
    private Integer floor;

    //---
    // 옥탑형
    private Boolean rooftop;

    // 반지하
    private Boolean basement;

    // 지하
    private Boolean underground;
    //---

    // 대표사진
    private ComplexImageResponseDto mainImageUrl;

    // 방 사진들
    private List<ComplexImageResponseDto> roomImages;

    private Double latitude;

    private Double longitude;

    // 주실 방향 기준
    private String roomDirection;

    // 주실 방향
    private String roomDirectionDetail;

    // 욕실수
    private Integer bathroomCount;

    // 주차가능
    private Integer parkable;

    // 엘리베이터 여부
    private Boolean elevatable;

    // 펫 여부
    private Boolean petable;

    // 대출 여부(LH전세대출 가능, 카카오뱅크 전세대출 가능)
    private String loan;

    // 여기서부터 옵션

    // 시설정보옵션들
    // 난방시설
    private HeatingFacilityDto heatingFacility;

    // 냉방시설
    private CoolingFacilityDto coolingFacility;

    // 생활시설
    private LivingFacilityDto livingFacility;

    // 보안시설
    private SecurityFacilityDto securityFacility;

    // 기타시설
    private EtcFacilityDto etcFacility;

    // 상세설명
    private String description;

    public static RealComplexDetailResponseDto fromEntity(RealComplex complex, ComplexImageResponseDto mainImage, List<ComplexImageResponseDto> roomImages){
        return RealComplexDetailResponseDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .tradeTypeName(complex.getTradeTypeName())
                .address(complex.getAddress())
                .detailAddress(complex.getDetailAddress())
                .supplyArea(complex.getSupplyArea())
                .dedicatedArea(complex.getDedicatedArea())
                .structure(complex.getStructure())
                .detailStructure(complex.getDetailStructure())
                .dealPrc(complex.getDealPrc())
                .warrantPrc(complex.getWarrantPrc())
                .maintenanceCost(complex.getMaintenanceCost())
                .maintenanceCostIncludings(MaintenanceCostIncludingsDto.fromEntity(complex.getMaintenanceCostIncludings()))
                .availableDate(complex.getAvailableDate())
                .availableDateNegotiable(complex.getAvailableDateNegotiable())
                .wholeFloor(complex.getWholeFloor())
                .floor(complex.getFloor())
                .rooftop(complex.getRooftop())
                .basement(complex.getBasement())
                .underground(complex.getUnderground())
                .mainImageUrl(mainImage)
                .roomImages(roomImages)
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .roomDirection(complex.getRoomDirection())
                .roomDirectionDetail(complex.getRoomDirectionDetail())
                .bathroomCount(complex.getBathroomCount())
                .parkable(complex.getParkable())
                .elevatable(complex.getElevatable())
                .petable(complex.getPetable())
                .loan(complex.getLoan())
                .heatingFacility(HeatingFacilityDto.fromEntity(complex.getHeatingFacility()))
                .coolingFacility(CoolingFacilityDto.fromEntity(complex.getCoolingFacility()))
                .livingFacility(LivingFacilityDto.fromEntity(complex.getLivingFacility()))
                .securityFacility(SecurityFacilityDto.fromEntity(complex.getSecurityFacility()))
                .etcFacility(EtcFacilityDto.fromEntity(complex.getEtcFacility()))
                .description(complex.getDescription())
                .build();
    }
}
