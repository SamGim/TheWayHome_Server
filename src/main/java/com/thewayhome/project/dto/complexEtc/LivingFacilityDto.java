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
    private Boolean airConditioner;
    private Boolean refrigerator;
    private Boolean washingMachine;
    private Boolean gasRange;
    private Boolean microwave;
    private Boolean induction;
    private Boolean desk;
    private Boolean bookshelf;
    private Boolean diningTable;
    private Boolean chair;
    private Boolean sofa;
    private Boolean shoeCloset;
    private Boolean showerBooth;
    private Boolean bathtub;
    private Boolean bidet;
    private Boolean sink;
    private Boolean dishwasher;
    private Boolean inductionStove;
    private Boolean gasOven;
public static LivingFacilityDto fromEntity(LivingFacility livingFacility){
        return LivingFacilityDto.builder()
                .airConditioner(livingFacility.getAirConditioner())
                .refrigerator(livingFacility.getRefrigerator())
                .washingMachine(livingFacility.getWashingMachine())
                .gasRange(livingFacility.getGasRange())
                .microwave(livingFacility.getMicrowave())
                .induction(livingFacility.getInduction())
                .desk(livingFacility.getDesk())
                .bookshelf(livingFacility.getBookshelf())
                .diningTable(livingFacility.getDiningTable())
                .chair(livingFacility.getChair())
                .sofa(livingFacility.getSofa())
                .shoeCloset(livingFacility.getShoeCloset())
                .showerBooth(livingFacility.getShowerBooth())
                .bathtub(livingFacility.getBathtub())
                .bidet(livingFacility.getBidet())
                .sink(livingFacility.getSink())
                .dishwasher(livingFacility.getDishwasher())
                .inductionStove(livingFacility.getInductionStove())
                .gasOven(livingFacility.getGasOven())
                .build();
    }

    public LivingFacility toEntity() {
        return LivingFacility.builder()
                .airConditioner(this.airConditioner)
                .refrigerator(this.refrigerator)
                .washingMachine(this.washingMachine)
                .gasRange(this.gasRange)
                .microwave(this.microwave)
                .induction(this.induction)
                .desk(this.desk)
                .bookshelf(this.bookshelf)
                .diningTable(this.diningTable)
                .chair(this.chair)
                .sofa(this.sofa)
                .shoeCloset(this.shoeCloset)
                .showerBooth(this.showerBooth)
                .bathtub(this.bathtub)
                .bidet(this.bidet)
                .sink(this.sink)
                .dishwasher(this.dishwasher)
                .inductionStove(this.inductionStove)
                .gasOven(this.gasOven)
                .build();
    }
}
