package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.user.entity.User;

import java.util.List;

public record TempPlaceLogPlaceCommand (
        String name,
        String description,
        List<PlaceRegisterCommand> places,
        Long placeLogId
) {
    public List<Place> toEntityList(User user){
        return places.stream()
                .map(placeRegisterCommand -> placeRegisterCommand.toEntity(user))
                .toList();
    }
}
