package com.spoteditor.backend.common.exceptions;

import com.spoteditor.backend.common.exceptions.response.ApiErrorResponse;
import com.spoteditor.backend.common.exceptions.user.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleUserException(UserException e) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(
                e.getHttpStatus(),
                e.getCustomStatus(),
                e.getCustomMessage()
        );

        return new ResponseEntity<>(apiErrorResponse, e.getHttpStatus());
    }
}
