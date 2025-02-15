package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class TokenException extends BusinessException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
