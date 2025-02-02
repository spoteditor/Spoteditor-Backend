package com.spoteditor.backend.image.service;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlRequest;

public interface PlaceImageService {

	PreSignedUrlResponse processPreSignedUrl(PreSignedUrlRequest request);
	PlaceImageResponse upload(String originalFile, String uuid, Long placeId);
}
