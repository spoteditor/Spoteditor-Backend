package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.user.entity.User;

import java.util.List;

public record PlaceLogRegisterCommand (
        List<Long> placeIds,
        String name,
        String description
) {
    public PlaceLog toEntity(User user, List<Place> places) {

        Place firstPlace = places.get(0);

        PlaceLog placeLog = PlaceLog.builder()
                .user(user)
                .name(name)
                .description(description)
                .imageUrl(firstPlace.getPlaceImages().get(0).getStoredFile())
                .address(firstPlace.getAddress())
                .build();

        List<PlaceLogPlaceMapping> placeLogPlaceMappings = places.stream()
                .map(place -> PlaceLogPlaceMapping.builder()
                        .placeLog(placeLog)
                        .place(place)
                        .build())
                .toList();

        placeLog.getPlaceLogPlaceMappings().addAll(placeLogPlaceMappings);

        return placeLog;
    }
}
