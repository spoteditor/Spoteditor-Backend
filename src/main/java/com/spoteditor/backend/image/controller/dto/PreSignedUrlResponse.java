package com.spoteditor.backend.image.controller.dto;

public record PreSignedUrlResponse(
		String preSignedUrl
) {

	public static PreSignedUrlResponse from(String preSignedUrl) {
		return new PreSignedUrlResponse(preSignedUrl);
	}
}
