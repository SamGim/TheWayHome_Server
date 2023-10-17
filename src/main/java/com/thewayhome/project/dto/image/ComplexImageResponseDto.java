package com.thewayhome.project.dto.image;

import com.thewayhome.project.domain.ComplexImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexImageResponseDto {
    private Long id;
    private String url;

    public static ComplexImageResponseDto fromEntity(ComplexImage complexImage){
        return ComplexImageResponseDto.builder()
                .id(complexImage.getId())
                .url(complexImage.getUrl())
                .build();
    }
}
