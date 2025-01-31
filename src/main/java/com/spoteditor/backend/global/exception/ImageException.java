package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class ImageException extends BusinessException {

	public ImageException(ErrorCode errorCode) {
		super(errorCode);
	}
}
