package com.thewayhome.project.dto.company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyGetFullNameResponseDto {
    String companyName;
    Long companyId;
}
