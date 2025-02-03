package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends BusinessException {

	public UserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
