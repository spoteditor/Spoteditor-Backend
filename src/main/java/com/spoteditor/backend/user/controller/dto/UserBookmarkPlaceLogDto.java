package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.entity.PlaceLog;

public record UserBookmarkPlaceLogDto (
    String userName,
    Long id,
    String name,
    PlaceImageResponse image,
    Address address
) {
    public static UserBookmarkPlaceLogDto from(PlaceLog placeLog) {
        return new UserBookmarkPlaceLogDto (
                placeLog.getUser().getName(),
                placeLog.getId(),
                placeLog.getName(),
                PlaceImageResponse.from(placeLog.getPlaceLogImage()),
                placeLog.getAddress()
        );
    }
}
