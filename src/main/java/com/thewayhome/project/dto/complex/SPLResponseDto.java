package com.thewayhome.project.dto.complex;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SPLResponseDto {
    private String stNodeName;
    private String stNodeSrcType;
    private String edNodeName;
    private String edNodeSrcType;
    private String cost;
    private String linkName;
    private String linkType;
}