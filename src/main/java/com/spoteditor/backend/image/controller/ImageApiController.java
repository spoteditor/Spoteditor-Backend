package com.spoteditor.backend.image.controller;

import com.spoteditor.backend.image.controller.dto.PresignedUrlRequest;
import com.spoteditor.backend.image.controller.dto.PresignedUrlResponse;
import com.spoteditor.backend.image.service.ImageService;
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
public class ImageApiController {

	private final ImageService imageService;

	@PostMapping("/purl")
	public ResponseEntity<PresignedUrlResponse> processPresignedUrlResponse(@RequestBody PresignedUrlRequest request) {

		PresignedUrlResponse presignedUrlResponse = imageService.processPresignedUrl(request);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(presignedUrlResponse);
	}
}
