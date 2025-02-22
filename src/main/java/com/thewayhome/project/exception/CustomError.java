package com.thewayhome.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomError {
    TEST_ERROR(404, "테스트 에러입니다."),
    NO_DATA_ERROR(404, "존재하지 않습니다."),
    WRONG_ID_ERROR(400, "유효한 ID가 아닙니다."),
    DB_SAVE_ERROR(404,"데이터베이스에 저장하지 못했습니다."),
    PARSE_ERROR(404, "파싱에 실패했습니다."),
    NAVER_MAPS_API_ERROR(500,"네이버맵스 api 요청결과 200이 아닙니다."),
    HTTP_PROTOCOL_ERROR(500, "유효하지 않은 HTTP 메소드입니다."),
    MALFORMED_URL_ERROR(500, "유효하지 않은 URL 입니다."),
    JSON_PROCESSING_ERROR(500, "JSON 파싱 에러"),
    IMAGE_UPLOAD_ERROR(500, "이미지 업로드 실패"),
    JAVA_IO_ERROR(500, "IOException"),
    DB_DELETE_ERROR(500, "DB에서 삭제 실패했습니다."),
    PTIS_SERVER_REQUEST_4XX_ERROR(500, "PTIS 서버 요청에 실패했습니다."),
    PTIS_SERVER_REQUEST_UNKNOWN_ERROR(500, "PTIS 서버 요청에 실패했습니다."),
    JSON_STRING_PARSING_ERROR(500, "JSON String 파싱 에러"),
    WEB_FLUX_ERROR(500, "WebFlux 에러"),

    NOT_A_MEMBER(402, "존재하지 않는 회원입니다."),
    ;



    private final int status;
    private final String message;
}
