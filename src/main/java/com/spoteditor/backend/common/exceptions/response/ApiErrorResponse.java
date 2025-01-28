package com.spoteditor.backend.common.exceptions.response;

import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

    private final HttpStatus httpStatus;
    private final String responseCode;
    private final String message;

    @Builder
    private ApiErrorResponse(HttpStatus status, UserErrorCode errorCode) {
        this.httpStatus = status;
        this.responseCode = errorCode != null ? errorCode.getCode() : null;
        this.message = errorCode != null ? errorCode.getMessage() : null;
    }
}
