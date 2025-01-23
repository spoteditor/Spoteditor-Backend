package com.spoteditor.backend.place.controller.dto;

import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.service.dto.PlaceUpdateCommand;

public record PlaceUpdateRequest(

		String name,
		String description,
		Category category
) {

	public PlaceUpdateCommand from() {
		return new PlaceUpdateCommand(
				this.name,
				this.description,
				this.category);
	}
}
