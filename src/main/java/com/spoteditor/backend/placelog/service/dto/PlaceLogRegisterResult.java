package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;

import java.util.List;

public record PlaceLogRegisterResult (
        List<Place> places,
        Long placeLogId,
        String name,
        String description,
        String image,
        Address address
) {
    public static PlaceLogRegisterResult from(PlaceLog placeLog, List<Place> places) {
        return new PlaceLogRegisterResult(
                places,
                placeLog.getId(),
                placeLog.getName(),
                placeLog.getDescription(),
                placeLog.getImageUrl(),
                placeLog.getAddress()
        );
    }
}
