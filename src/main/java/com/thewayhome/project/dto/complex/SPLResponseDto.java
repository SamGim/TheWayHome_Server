package com.thewayhome.project.dto.complex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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