package com.thewayhome.project.dto.complex;

import com.thewayhome.project.domain.*;
import com.thewayhome.project.dto.complexEtc.*;
import jakarta.persistence.*;
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

    // 임대료
    private String dealPrc;

    // 보증금
    private String warrantPrc;

    // 관리비(단위: 만원)
    private String maintenanceCost;

    // 관리비 포함내역(전기세, 가스, 인터넷, 수도, TV)
    private MaintenanceCostIncludingsDto maintenanceCostIncludings;

    // 입주가능일
    private String availableDate;

    // 입주협가능여부
    private Boolean availableDateNegotiable;

    // 전체층
    private Integer wholeFloor;

    // 해당층
    private Integer floor;

    // 옥탑형
    private Boolean rooftop;

    // 반지하
    private Boolean basement;

    // 지하
    private Boolean underground;


    private Double latitude;

    private Double longitude;


    // 여기서부터 옵션
    // 주차가능
    private Boolean parkable;

    // 엘리베이터 여부
    private Boolean elevatable;

    // 옵션(싱크대, 에어컨, 세탁기, ...etc)
    private String options;

    // 매물 설명
    private String details;

    // 대출 여부(LH전세대출 가능, 카카오뱅크 전세대출 가능)
    private String loan;

    // 펫 여부
    private Boolean petable;

    // 욕실수
    private Integer bathroomCount;

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

    private String description;

    public RealComplex toEntity() throws ParseException {
        Point point = (latitude != null && longitude != null)
                ? (Point) new WKTReader().read(String.format("POINT(%s %s)", latitude, longitude))
                : null;
        return RealComplex.builder()
                .name(name)
                .tradeTypeName(tradeTypeName)
                .address(address)
                .detailAddress(detailAddress)
                .supplyArea(supplyArea)
                .dedicatedArea(dedicatedArea)
                .structure(structure)
                .dealPrc(dealPrc)
                .warrantPrc(warrantPrc)
                .maintenanceCost(maintenanceCost)
                .availableDate(availableDate)
                .availableDateNegotiable(availableDateNegotiable)
                .wholeFloor(wholeFloor)
                .floor(floor)
                .latitude(latitude)
                .longitude(longitude)
                .parkable(parkable)
                .elevatable(elevatable)
                .options(options)
                .details(details)
                .loan(loan)
                .petable(petable)
                .bathroomCount(bathroomCount)
                .description(description)
                .location(point)
                .build();
    }
}

