package com.spoteditor.backend.place.service.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.entity.Place;

public record PlaceRegisterResult(

		//Long userId,
		Long placeId,
		String name,
		String description,
		int bookmark,
		Address address,
		Category category
) {

	public static PlaceRegisterResult from(Place place) {
		return new PlaceRegisterResult(
				//place.getUser().getId(),
				place.getId(),
				place.getName(),
				place.getDescription(),
				place.getBookmark(),
				place.getAddress(),
				place.getCategory()
		);
	}
}
