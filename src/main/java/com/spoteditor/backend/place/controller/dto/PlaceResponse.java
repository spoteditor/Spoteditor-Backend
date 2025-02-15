package com.spoteditor.backend.place.controller.dto;

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
		Category category
) {
	public static PlaceResponse from (Place place) {
		return new PlaceResponse(
				place.getId(),
				place.getUser().getName(),
				place.getName(),
				place.getDescription(),
				place.getAddress(),
				place.getCategory()
		);
	}
}
