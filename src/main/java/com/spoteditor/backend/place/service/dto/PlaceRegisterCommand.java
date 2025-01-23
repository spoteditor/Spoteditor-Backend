package com.spoteditor.backend.place.service.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.entity.Place;

public record PlaceRegisterCommand(

		String name,
		String description,
		String originalFile,
		String path,
		Address address,
		Category category
) {

	public Place toEntity() {
		return Place.builder()
				.user(null)
				.address(address)
				.description(description)
				.bookmark(0)
 				.category(category)
				.name(name)
				.build();
	}

}
