package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class FollowException extends BusinessException {

	public FollowException(ErrorCode errorCode) {
		super(errorCode);
	}
}
