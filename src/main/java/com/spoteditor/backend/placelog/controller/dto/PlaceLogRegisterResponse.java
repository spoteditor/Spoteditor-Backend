package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.controller.dto.PlaceRegisterResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterResult;

import java.util.List;

public record PlaceLogRegisterResponse (
        Long placeLogId,
        String name,
        String description,
        String image,
        Address address,
        List<PlaceLogPlaceRegisterResponse> content
) {
    public static PlaceLogRegisterResponse from (PlaceLogRegisterResult result) {
        return new PlaceLogRegisterResponse(
                result.placeLogId(),
                result.name(),
                result.description(),
                result.image(),
                result.address(),
                result.places()
                        .stream()
                        .map(PlaceLogPlaceRegisterResponse::from)
                        .toList()
        );
    }
}