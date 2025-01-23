package com.spoteditor.backend.place.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;

public record PlaceRegisterResponse(

		//Long userId,
		Long placeId,
		String name,
		String description,
		int bookmark,
		Address address,
		Category category
) {

	public static PlaceRegisterResponse from(PlaceRegisterResult result) {
		return new PlaceRegisterResponse(
				//result.userId(),
				result.placeId(),
                result.name(),
                result.description(),
				result.bookmark(),
                result.address(),
				result.category()
        );
	}
}
