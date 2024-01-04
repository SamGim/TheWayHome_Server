package com.thewayhome.project.dto.complex;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealComplexDetailWithPathResponseDto {
    private RealComplexDetailResponseDto complex;
    private List<SPLResponseDto> path;
}
