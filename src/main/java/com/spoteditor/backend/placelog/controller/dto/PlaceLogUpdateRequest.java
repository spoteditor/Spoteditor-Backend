package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.placelog.service.dto.PlaceLogPlaceCommand;

import java.util.List;

public record PlaceLogPlaceRequest(
    String name,
    String description,
    List<PlaceRegisterRequest> places
) {
    public PlaceLogPlaceCommand from(Long placeLogId) {
        return new PlaceLogPlaceCommand(
                name,
                description,
                places.stream()
                        .map(PlaceRegisterRequest::from)
                        .toList(),
                placeLogId
        );
    }
}
