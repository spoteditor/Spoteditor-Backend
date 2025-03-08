package com.spoteditor.backend.placelog.controller.dto;

public record PlaceLogBookmarkResponse(
	Long placeId,
	boolean isBookmarked
) {
}
