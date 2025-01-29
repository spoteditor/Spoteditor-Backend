package com.spoteditor.backend.common.exceptions.response;

import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Builder
    private ApiErrorResponse(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public static ApiErrorResponse of(HttpStatus httpStatus, String code, String message) {
        return ApiErrorResponse.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}
