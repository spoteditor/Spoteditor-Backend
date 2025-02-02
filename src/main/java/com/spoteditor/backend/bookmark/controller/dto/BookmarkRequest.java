package com.spoteditor.backend.bookmark.controller.dto;

import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;

public record BookmarkRequest(

		Long placeId
) {

	public BookmarkCommand from() {
		return new BookmarkCommand(
				this.placeId
		);
	}
}
