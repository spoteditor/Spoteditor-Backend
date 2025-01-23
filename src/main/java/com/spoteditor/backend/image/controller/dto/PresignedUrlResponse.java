package com.spoteditor.backend.image.controller.dto;

public record PresignedUrlResponse(
		String originalFile,
		String url
) {

	public static PresignedUrlResponse from(String originalFile, String url) {
		return new PresignedUrlResponse(originalFile, url);
	}
}
