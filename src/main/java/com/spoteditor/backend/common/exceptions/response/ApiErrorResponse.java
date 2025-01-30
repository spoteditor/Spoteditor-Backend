package com.spoteditor.backend.common.exceptions.response;

import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    @Builder
    private ApiErrorResponse(HttpStatus httpStatus, String code, String message, LocalDateTime timestamp) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ApiErrorResponse of(HttpStatus httpStatus, String code, String message, LocalDateTime timestamp) {
        return ApiErrorResponse.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .timestamp(timestamp)
                .build();
    }

    public static ApiErrorResponse of(HttpStatus httpStatus, String code, String message) {
        return of(httpStatus, code, message, LocalDateTime.now());
    }
}
