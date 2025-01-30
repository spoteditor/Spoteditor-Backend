package com.spoteditor.backend.common.exceptions.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {
    // 400 Bad Request
    UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "U400", "지원하지 않는 Provider"),

    // 401 Unauthorized
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "U401", "토큰 만료"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "U401", "AccessToken 만료"),
    NO_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "U401", "현재 로그인된 사용자가 없음"),

    // 403 Forbidden
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "U403", "유효하지 않은 토큰"),
    INVALID_TOKEN_NO_UID(HttpStatus.FORBIDDEN, "U403", "유효하지 않은 토큰: UID 없음"),
    REFRESH_TOKEN_INVALID(HttpStatus.FORBIDDEN, "U403", "유효하지 않은 RefreshToken"),
    REFRESH_TOKEN_NOT_IN_COOKIE(HttpStatus.FORBIDDEN, "U403", "쿠키에 RefreshToken 없음");


    private final HttpStatus httpStatus;
    private final String customStatus;
    private final String message;
}
