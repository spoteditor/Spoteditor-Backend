package com.spoteditor.backend.bookmark.controller.dto;

import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;
import lombok.Builder;

@Builder
public record BookmarkRequest(

		Long placeId
) {

	public BookmarkCommand toCommandDto() {
		return new BookmarkCommand(
				this.placeId
		);
	}
}
