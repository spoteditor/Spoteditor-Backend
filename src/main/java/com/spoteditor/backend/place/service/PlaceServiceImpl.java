package com.spoteditor.backend.place.service;

import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.image.service.PlaceImageService;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;
	private final PlaceImageService imageService;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public PlaceRegisterResult addPlace(Long userId, PlaceRegisterCommand command) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		Place savedPlace = placeRepository.save(command.toEntity(user));
		imageService.upload(command.originalFile(), savedPlace.getId());
		return PlaceRegisterResult.from(savedPlace);
	}

}
