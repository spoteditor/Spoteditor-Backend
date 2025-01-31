package com.spoteditor.backend.image.service;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlResponse;
import com.spoteditor.backend.image.controller.dto.PresignedUrlRequest;

public interface PlaceImageService {

	PreSignedUrlResponse processPresignedUrl(PresignedUrlRequest request);
	PlaceImageResponse upload(String originalFile, Long placeId);
}
