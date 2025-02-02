package com.spoteditor.backend.place.service.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.user.entity.User;

public record PlaceRegisterCommand(

		String name,
		String description,
		String originalFile,
		String uuid,
		Address address,
		Category category
) {

	public Place toEntity(User user) {
		return Place.builder()
				.user(user)
				.address(address)
				.description(description)
 				.category(category)
				.name(name)
				.build();
	}

}
