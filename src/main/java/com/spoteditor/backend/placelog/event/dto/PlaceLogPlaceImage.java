package com.spoteditor.backend.placelog.event.dto;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.entity.PlaceImage;

public record PlaceLogPlaceImage(
        String originalFile,
        String uuid,
        String storedFile
) {
    public static PlaceLogPlaceImage from(PlaceImageResponse response, String uuid) {
        return new PlaceLogPlaceImage(
                response.originalFile(),
                uuid,
                response.storedFile()
        );
    }

    public static PlaceLogPlaceImage from(PlaceImage image) {
        return new PlaceLogPlaceImage(
                null,
                null,
                image.getStoredFile()
        );
    }
}
