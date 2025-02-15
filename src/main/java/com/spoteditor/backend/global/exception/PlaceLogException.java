package com.spoteditor.backend.global.exception;

import com.spoteditor.backend.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class PlaceLogException extends BusinessException {

    public PlaceLogException(ErrorCode errorCode) { super(errorCode); }
}
