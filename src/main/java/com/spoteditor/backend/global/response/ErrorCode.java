package com.spoteditor.backend.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// user
	UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "U001", "지원하지 않는 Provider"),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "U002", "토큰 만료"),
	ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "U003", "AccessToken 만료"),
	NO_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "U004", "현재 로그인된 사용자가 없음"),
	INVALID_TOKEN(HttpStatus.FORBIDDEN, "U005", "유효하지 않은 토큰"),
	INVALID_TOKEN_NO_UID(HttpStatus.FORBIDDEN, "U006", "유효하지 않은 토큰: UID 없음"),
	REFRESH_TOKEN_INVALID(HttpStatus.FORBIDDEN, "U007", "유효하지 않은 RefreshToken"),
	REFRESH_TOKEN_NOT_IN_COOKIE(HttpStatus.FORBIDDEN, "U008", "쿠키에 RefreshToken 없음"),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U009", "해당 유저를 찾을 수 없습니다."),

	// place
	NOT_FOUND_PLACE(HttpStatus.NOT_FOUND, "P001", "해당 장소를 찾을 수 없습니다."),

	// bookmark
	NOT_FOUND_BOOKMARK(HttpStatus.NOT_FOUND, "B001", "해당 북마크를 찾을 수 없습니다."),
	BOOKMARK_PROCESSING_FAILED(HttpStatus.CONFLICT, "B002", "북마크 처리에 실패했습니다. 잠시 후 다시 시도해주세요."),

	// image
	NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "I001", "이미지를 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}