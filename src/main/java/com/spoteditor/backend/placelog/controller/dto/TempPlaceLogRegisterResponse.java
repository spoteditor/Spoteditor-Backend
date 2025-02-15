package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.placelog.service.dto.TempPlaceLogRegisterResult;

public record TempPlaceLogRegisterResponse(
        Long tempPlaceLogId
) {
    public static TempPlaceLogRegisterResponse from(TempPlaceLogRegisterResult result) {
        return new TempPlaceLogRegisterResponse(
                result.placeLog().getId()
        );
    }
}
