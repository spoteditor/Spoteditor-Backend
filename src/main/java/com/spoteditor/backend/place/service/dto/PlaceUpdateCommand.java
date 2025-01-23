package com.spoteditor.backend.place.service.dto;

import com.spoteditor.backend.place.entity.Category;

public record PlaceUpdateCommand(
		String name,
		String description,
		Category category
) {

}
