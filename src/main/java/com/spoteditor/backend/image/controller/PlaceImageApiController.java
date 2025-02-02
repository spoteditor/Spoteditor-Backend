package com.spoteditor.backend.image.controller;

import com.spoteditor.backend.image.controller.dto.PreSignedUrlResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlRequest;
import com.spoteditor.backend.image.service.PlaceImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceImageApiController {

	private final PlaceImageService imageService;

	@PostMapping("/purl")
	public ResponseEntity<PreSignedUrlResponse> processPreSignedUrlResponse(@RequestBody PreSignedUrlRequest request) {

		PreSignedUrlResponse presignedUrlResponse = imageService.processPreSignedUrl(request);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(presignedUrlResponse);
	}
}
