package com.spoteditor.backend.place.controller;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.place.controller.dto.PlaceRegisterResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.PlaceService;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "place", description = "장소 API")
public class PlaceApiController {

	private final PlaceService placeService;
	private final PlaceRepository placeRepository;

	/**
	 *
	 * @param dto
	 * @param request
	 * @return
	 */
	@PostMapping("/places")
	public ResponseEntity<PlaceRegisterResponse> addPlace(@AuthenticationPrincipal UserIdDto dto,
														  @RequestBody final PlaceRegisterRequest request) {

		PlaceRegisterCommand command = request.from();
		PlaceRegisterResult result = placeService.addPlace(dto.getId(), command);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(PlaceRegisterResponse.from(result));
	}

	/**
	 *
	 * @param pageRequest
	 * @return
	 */
	@GetMapping("/places")
	public ResponseEntity<CustomPageResponse<PlaceResponse>> retrievePlace(CustomPageRequest pageRequest) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(placeRepository.findAllPlace(pageRequest));
	}
}
