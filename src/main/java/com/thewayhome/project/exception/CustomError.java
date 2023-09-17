package com.thewayhome.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomError {
    TEST_ERROR(404, "테스트 에러입니다."),
    NO_DATA_ERROR(404, "존재하지 않습니다."),
    WRONG_ID_ERROR(400, "유효한 ID가 아닙니다."),
    DB_SAVE_ERROR(404,"데이터베이스에 저장하지 못했습니다.");


    private final int status;
    private final String message;
}
