package com.spoteditor.backend.image.event.dto;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;

public record S3Image(
        String originalFile,
        String uuid,
        String storedFile
) {
    public static S3Image from(PlaceImageResponse response, String uuid) {
        return new S3Image(
                response.originalFile(),
                uuid,
                response.storedFile()
        );
    }

    public static S3Image from(com.spoteditor.backend.image.entity.PlaceImage image) {
        return new S3Image(
                null,
                null,
                image.getStoredFile()
        );
    }
}
