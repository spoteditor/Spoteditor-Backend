package com.spoteditor.backend.global.response;

import com.spoteditor.backend.global.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
		ErrorResponse apiErrorResponse = ErrorResponse.of(
				e.getErrorCode()
		);

		return ResponseEntity
				.status(apiErrorResponse.getStatus())
				.body(apiErrorResponse);
	}
}