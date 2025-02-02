package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	protected BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	protected BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
