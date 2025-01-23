package com.spoteditor.backend.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// user
	NOT_FOUND_USER(404, "U001", "해당 유저를 찾을 수 없습니다."),

	// place
	NOT_FOUND_PLACE(404, "P001", "해당 장소를 찾을 수 없습니다."),

	// bookmark
	NOT_FOUND_BOOKMARK(404, "B001", "해당 북마크를 찾을 수 없습니다.");

	private final int status;
	private final String code;
	private final String message;
}
