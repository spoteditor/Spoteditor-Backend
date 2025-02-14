package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.placelog.service.dto.TempPlaceLogPlaceCommand;

import java.util.List;

public record TempPlaceLogPlaceRequest(
    String name,
    String description,
    List<PlaceRegisterRequest> places
) {
    public TempPlaceLogPlaceCommand from(Long placeLogId) {
        return new TempPlaceLogPlaceCommand(
                name,
                description,
                places.stream()
                        .map(PlaceRegisterRequest::from)
                        .toList(),
                placeLogId
        );
    }
}
