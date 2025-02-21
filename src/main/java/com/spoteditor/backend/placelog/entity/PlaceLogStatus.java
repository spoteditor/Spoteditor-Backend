package com.spoteditor.backend.placelog.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.spoteditor.backend.global.exception.PlaceLogException;

public enum PlaceLogStatus {
    PUBLIC,
    PRIVATE;

    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static PlaceLogStatus fromJson(String value) {
        for (PlaceLogStatus status : PlaceLogStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new PlaceLogException("해당 status 값은 존재하지 않습니다.");
    }
}
