package com.spoteditor.backend.place.controller;

import com.spoteditor.backend.config.page.PageRequest;
import com.spoteditor.backend.config.page.PageResponse;
import com.spoteditor.backend.place.controller.dto.*;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.PlaceService;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaceApiController {

	private final PlaceService placeService;
	private final PlaceRepository placeRepository;

	@PostMapping("/places")
	public PlaceRegisterResponse addPlaceByZipCode(@RequestBody final PlaceRegisterRequest request) {

		PlaceRegisterCommand command = request.from();
		PlaceRegisterResult result = placeService.addPlace(command);
		return PlaceRegisterResponse.from(result);
	}

	@GetMapping("/places")
	public PageResponse<PlaceResponse> retrievePlace(PageRequest pageRequest) {

		return placeRepository.findAllPlace(pageRequest);
	}
}
