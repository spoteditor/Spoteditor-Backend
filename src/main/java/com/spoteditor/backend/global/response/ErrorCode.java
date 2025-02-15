package com.spoteditor.backend.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// token
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T001", "토큰 만료"),
	ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T002", "AccessToken 만료"),
	INVALID_TOKEN(HttpStatus.FORBIDDEN, "T003", "유효하지 않은 토큰"),
	INVALID_TOKEN_NO_UID(HttpStatus.FORBIDDEN, "T004", "유효하지 않은 토큰: UID 없음"),
	REFRESH_TOKEN_INVALID(HttpStatus.FORBIDDEN, "T005", "유효하지 않은 RefreshToken"),
	REFRESH_TOKEN_NOT_IN_COOKIE(HttpStatus.FORBIDDEN, "T006", "쿠키에 RefreshToken 없음"),

	// user
	UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "U001", "지원하지 않는 Provider"),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U002", "해당 유저를 찾을 수 없습니다."),
	NO_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "U003", "현재 사용자의 권한이 없습니다."),
	DELETED_USER(HttpStatus.BAD_REQUEST, "U004", "삭제된 유저입니다."),
	NEED_ADMIN_ROLE(HttpStatus.FORBIDDEN, "U005", "관리자 권한이 필요합니다."),
	USER_ROLE_MISSING(HttpStatus.FORBIDDEN, "U006", "사용자의 역할이 없습니다."),

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