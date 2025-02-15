package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;

public class TagException extends BusinessException {

    public TagException(ErrorCode errorCode) { super(errorCode); }
}
