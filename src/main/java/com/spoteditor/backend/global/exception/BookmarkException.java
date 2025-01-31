package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookmarkException extends BusinessException {

	public BookmarkException(ErrorCode errorCode) {
		super(errorCode);
	}
}
