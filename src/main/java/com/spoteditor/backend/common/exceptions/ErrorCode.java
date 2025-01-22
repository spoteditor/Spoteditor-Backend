package com.spoteditor.backend.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 Bad Request
    UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "4001", "지원하지 않는 Provider"),

    // 401 Unauthorized
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"4011", "AccessToken 만료"),

    // 403 Forbidden
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "4031", "유효하지 않은 토큰"),
    INVALID_TOKEN_NO_UID(HttpStatus.FORBIDDEN,"4032", "유효하지 않은 토큰: UID 없음"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.FORBIDDEN, "4033", "RefreshToken 만료"),
    REFRESH_TOKEN_NOT_IN_COOKIE(HttpStatus.FORBIDDEN, "4034", "쿠키에 RefreshToken 없음"),
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
