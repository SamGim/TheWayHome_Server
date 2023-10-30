package com.thewayhome.project.dto.company;

import com.thewayhome.project.domain.Company;
import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.dto.complex.ComplexSimpleResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponseDto {
    private String companyName;
    private String address;
    private Double latitude;
    private Double longitude;

    public static CompanyResponseDto fromEntity(Company company) {
        return CompanyResponseDto.builder()
                .companyName(company.getCompanyName())
                .address(company.getAddress())
                .latitude(company.getLatitude())
                .longitude(company.getLongitude())
                .build();
    }

}


