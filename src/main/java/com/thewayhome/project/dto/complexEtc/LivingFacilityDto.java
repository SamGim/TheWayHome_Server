package com.thewayhome.project.dto.complexEtc;

import com.thewayhome.project.domain.LivingFacility;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivingFacilityDto {
    // 냉장고
    private Boolean refrigerator;

    // 세탁기
    private Boolean washingMachine;

    // 가스레인지
    private Boolean gasRange;

    // 전자레인지
    private Boolean microwave;

    // 인덕션
    private Boolean induction;

    // 책상
    private Boolean desk;

    // 식탁
    private Boolean diningTable;

    // 침대
    private Boolean bed;

    // 옷장
    private Boolean closet;

    // 건조기
    private Boolean dryer;

    // 붙박이장
    private Boolean builtInCloset;

    // 소파
    private Boolean sofa;

    // 신발장
    private Boolean shoeCloset;

    // 샤워부스
    private Boolean showerBooth;

    // 욕조
    private Boolean bathtub;

    // 비데
    private Boolean bidet;

    // 싱크대
    private Boolean sink;

    // 식기세척기
    private Boolean dishwasher;

    // 가스오븐
    private Boolean gasOven;

    // TV
    private Boolean tv;

    // 기타
    private String etc;
public static LivingFacilityDto fromEntity(LivingFacility livingFacility){
        return LivingFacilityDto.builder()
                .refrigerator(livingFacility.getRefrigerator())
                .washingMachine(livingFacility.getWashingMachine())
                .gasRange(livingFacility.getGasRange())
                .microwave(livingFacility.getMicrowave())
                .induction(livingFacility.getInduction())
                .desk(livingFacility.getDesk())
                .diningTable(livingFacility.getDiningTable())
                .bed(livingFacility.getBed())
                .closet(livingFacility.getCloset())
                .dryer(livingFacility.getDryer())
                .builtInCloset(livingFacility.getBuiltInCloset())
                .sofa(livingFacility.getSofa())
                .shoeCloset(livingFacility.getShoeCloset())
                .showerBooth(livingFacility.getShowerBooth())
                .bathtub(livingFacility.getBathtub())
                .bidet(livingFacility.getBidet())
                .sink(livingFacility.getSink())
                .dishwasher(livingFacility.getDishwasher())
                .gasOven(livingFacility.getGasOven())
                .tv(livingFacility.getTv())
                .etc(livingFacility.getEtc())
                .build();
    }

    public LivingFacility toEntity() {
        return LivingFacility.builder()
                .refrigerator(refrigerator)
                .washingMachine(washingMachine)
                .gasRange(gasRange)
                .microwave(microwave)
                .induction(induction)
                .desk(desk)
                .diningTable(diningTable)
                .bed(bed)
                .closet(closet)
                .dryer(dryer)
                .builtInCloset(builtInCloset)
                .sofa(sofa)
                .shoeCloset(shoeCloset)
                .showerBooth(showerBooth)
                .bathtub(bathtub)
                .bidet(bidet)
                .sink(sink)
                .dishwasher(dishwasher)
                .gasOven(gasOven)
                .tv(tv)
                .etc(etc)
                .build();
    }
}
