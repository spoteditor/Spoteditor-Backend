package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.entity.Place;

public record UserBookmarkPlaceDto (
    String userName,
    Long id,
    String name,
    String imageUrl,
    Address address,
    Category category
) {
    public static UserBookmarkPlaceDto from(Place place) {
        return new UserBookmarkPlaceDto(
                place.getUser().getName(),
                place.getId(),
                place.getName(),
                place.getPlaceImages().get(0).getStoredFile(),
                place.getAddress(),
                place.getCategory()
        );
    }
}
