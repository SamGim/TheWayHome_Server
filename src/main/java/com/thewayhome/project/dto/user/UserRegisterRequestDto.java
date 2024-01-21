package com.thewayhome.project.dto.user;


import com.thewayhome.project.domain.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegisterRequestDto {
    private String nickname;
    private String phoneNumber;
    private String token;

    public User toEntity(Object object) {
        return User.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .build();
    }
}
/*
닉네임
경력
성별
키
몸무게
체지방율
근육량
 */