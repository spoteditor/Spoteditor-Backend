package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.entity.PlaceLog;

public record UserPlaceLogDto (
    String userName,
    Long id,
    String name,
    String imageUrl,
    Address address
) {
    public static UserPlaceLogDto from(PlaceLog placeLog) {
        return new UserPlaceLogDto (
                placeLog.getUser().getName(),
                placeLog.getId(),
                placeLog.getName(),
                placeLog.getImageUrl(),
                placeLog.getAddress()
        );
    }
}
