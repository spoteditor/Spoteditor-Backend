package com.spoteditor.backend.place.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;

public record PlaceResponse(

		Long placeId,
		String author,
		String name,
		String description,
		Address address,
		Category category
) {
}
