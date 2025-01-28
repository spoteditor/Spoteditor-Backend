package com.spoteditor.backend.common.exceptions;

import com.spoteditor.backend.common.exceptions.user.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, String>> handleUserException(UserException e) {
        Map<String, String> response = new HashMap<>();
        response.put("code", e.getCustomStatus());
        response.put("message", e.getCustomMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }
}
