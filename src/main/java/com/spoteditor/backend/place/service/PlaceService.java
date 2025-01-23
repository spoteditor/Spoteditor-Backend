package com.spoteditor.backend.place.service;

import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.place.service.dto.PlaceUpdateCommand;
import org.springframework.stereotype.Service;

@Service
public interface PlaceService {

	PlaceRegisterResult addPlace(PlaceRegisterCommand command);
	void updatePlace(Long placeId, PlaceUpdateCommand command);
	void deletePlace(Long placeId);
}
