package com.spoteditor.backend.place.controller.dto;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.entity.Place;
import lombok.Builder;

@Builder
public record PlaceResponse(

		Long placeId,
		String author,
		String name,
		String description,
		Address address,
		Category category,
		PlaceImageResponse image
) {
}
