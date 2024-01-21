package com.thewayhome.project.dto.user;


import com.thewayhome.project.domain.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String nickname;
    private String username;
    private String provider;
    private String refreshToken;


    public UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .provider(user.getProvider())
                .refreshToken(user.getRefreshToken())
                .build();
    }
}
