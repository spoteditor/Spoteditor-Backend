package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class PlaceException extends BusinessException {

	public PlaceException(ErrorCode errorCode) {
		super(errorCode);
	}
}
