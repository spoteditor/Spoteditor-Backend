package com.spoteditor.backend.place.service;

import com.spoteditor.backend.global.exception.PlaceException;
import com.spoteditor.backend.image.service.ImageService;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.place.service.dto.PlaceUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_PLACE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;
	private final ImageService imageService;

	@Override
	@Transactional
	public PlaceRegisterResult addPlace(PlaceRegisterCommand command) {
		Place savedPlace = placeRepository.save(command.toEntity());
		imageService.upload(command.originalFile(), command.path(), savedPlace.getId());
		return PlaceRegisterResult.from(savedPlace);
	}

	@Override
	@Transactional
	public void updatePlace(Long placeId, PlaceUpdateCommand command) {

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));
		place.update(command.name(), command.description(), command.category());
	}

	@Override
	@Transactional
	public void deletePlace(Long placeId) {

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));
		placeRepository.delete(place);
	}

}
