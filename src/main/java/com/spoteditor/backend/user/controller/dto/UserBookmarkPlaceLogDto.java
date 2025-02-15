package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.entity.PlaceLog;

public record UserBookmarkPlaceLogDto (
    String userName,
    Long id,
    String name,
    String imageUrl,
    Address address
) {
    public static UserBookmarkPlaceLogDto from(PlaceLog placeLog) {
        return new UserBookmarkPlaceLogDto (
                placeLog.getUser().getName(),
                placeLog.getId(),
                placeLog.getName(),
                placeLog.getImageUrl(),
                placeLog.getAddress()
        );
    }
}
