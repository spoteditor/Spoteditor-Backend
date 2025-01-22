package com.spoteditor.backend.common.exceptions;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private HttpStatus httpStatus;
    private ErrorCode errorCode;

    public BaseException(ErrorCode errorCode){
        super(errorCode != null ? errorCode.getMessage() : "ErrorCode 에 등록되지 않은 에러입니다.");
        this.httpStatus = errorCode != null ? errorCode.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = errorCode;
    }
}
