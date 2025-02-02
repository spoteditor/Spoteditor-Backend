package com.spoteditor.backend.image.controller.dto;

import com.spoteditor.backend.image.entity.PlaceImage;

public record PlaceImageResponse(
		Long imageId,
		String originalFile,
		String storedFile
) {

	public static PlaceImageResponse from(PlaceImage image) {
		return new PlaceImageResponse(image.getId(), image.getOriginalFile(), image.getStoredFile());
	}
}
