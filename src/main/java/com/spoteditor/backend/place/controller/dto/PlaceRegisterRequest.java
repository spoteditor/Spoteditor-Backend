package com.spoteditor.backend.place.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;

public record PlaceRegisterRequest(
		String name,
		String description,
		String originalFile,
		String path,
		Address address,
		Category category
) {

	public PlaceRegisterCommand from() {
		return new PlaceRegisterCommand(
				this.name,
				this.description,
				this.originalFile,
				this.path,
				this.address,
				this.category
		);
	}
}
