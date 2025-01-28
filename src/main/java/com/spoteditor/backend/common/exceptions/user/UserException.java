package com.spoteditor.backend.common.exceptions.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {

    private HttpStatus httpStatus;
    private String customStatus;
    private String customMessage;

    public UserException(UserErrorCode errorCode){
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
        this.customStatus = "U" + errorCode.getHttpStatus().toString();
        this.customMessage = errorCode.getMessage();
    }
}
