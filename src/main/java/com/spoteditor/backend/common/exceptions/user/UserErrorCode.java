package com.spoteditor.backend.common.exceptions.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {
    // 400 Bad Request
    UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 Provider"),

    // 401 Unauthorized
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰 만료"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AccessToken 만료"),

    // 403 Forbidden
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰"),
    INVALID_TOKEN_NO_UID(HttpStatus.FORBIDDEN, "유효하지 않은 토큰: UID 없음"),
    REFRESH_TOKEN_INVALID(HttpStatus.FORBIDDEN, "유효하지 않은 RefreshToken"),
    REFRESH_TOKEN_NOT_IN_COOKIE(HttpStatus.FORBIDDEN, "쿠키에 RefreshToken 없음");


    private final HttpStatus httpStatus;
    private final String message;
}
