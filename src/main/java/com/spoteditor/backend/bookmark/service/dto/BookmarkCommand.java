package com.spoteditor.backend.bookmark.service.dto;


public record BookmarkCommand(
		Long userId,
		Long placeId
) {

}
