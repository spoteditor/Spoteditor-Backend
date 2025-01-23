package com.spoteditor.backend.image.service;

import com.spoteditor.backend.image.controller.dto.PresignedUrlRequest;
import com.spoteditor.backend.image.controller.dto.PresignedUrlResponse;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {

	PresignedUrlResponse processPresignedUrl(PresignedUrlRequest request);
	void upload(String originalFile, String path, Long placeId);
}
