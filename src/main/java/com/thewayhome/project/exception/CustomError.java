package com.thewayhome.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomError {
    TEST_ERROR(404, "테스트 에러입니다.");
    private final int status;
    private final String message;
}
