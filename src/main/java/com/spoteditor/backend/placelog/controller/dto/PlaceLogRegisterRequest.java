package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterCommand;

import java.util.List;

public record PlaceLogRegisterRequest(
        List<Long> placeIds,
        String name,
        String description
) {
    public PlaceLogRegisterCommand from() {
        return new PlaceLogRegisterCommand(
                this.placeIds(),
                this.name(),
                this.description()
        );
    }
}
