package com.thewayhome.project.dto.company;

import com.thewayhome.project.domain.Company;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegisterRequestDto {
    private String companyName;
    private String address;
    private Double latitude;
    private Double longitude;

    public Company toEntity(){
        return Company.builder()
                .companyName(companyName)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
