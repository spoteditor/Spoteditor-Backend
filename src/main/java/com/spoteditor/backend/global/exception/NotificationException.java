package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class NotificationException extends BusinessException {

	public NotificationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
