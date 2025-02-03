package com.spoteditor.backend.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

	private final HttpStatus status;
	private final String code;
	private final String message;
	private final LocalDateTime timestamp;

	private ErrorResponse(final ErrorCode code) {
		this.status = code.getStatus();
		this.code = code.getCode();
		this.message = code.getMessage();
		this.timestamp = LocalDateTime.now();
	}

	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}
}
