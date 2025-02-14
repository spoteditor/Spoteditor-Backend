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
	BOOKMARK_ALREADY_EXIST(HttpStatus.CONFLICT, "B003", "북마크가 이미 존재합니다."),
	BOOKMARK_ALREADY_REMOVED(HttpStatus.CONFLICT, "B004", "북마크가 존재하지 않습니다."),

	// image
	NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "I001", "이미지를 찾을 수 없습니다."),

	// place log
	NOT_FOUND_PLACES(HttpStatus.NOT_FOUND, "PL001", "로그에 등록된 장소들을 찾을 수 없습니다."),
	PLACE_MINIMUM_REQUIRED(HttpStatus.BAD_REQUEST, "PL002", "로그에 장소는 최소 1개 이상 등록해야 합니다."),
	PLACE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "PL003", "로그에 장소는 최대 10개까지 등록 가능합니다."),
	TEMP_PLACE_LOG_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "PL004", "임시저장은 1개까지 등록 가능합니다."),
	NOT_FOUND_PLACE_LOG(HttpStatus.BAD_REQUEST, "PL005", "로그를 찾을 수 없습니다."),
	NOT_PLACE_LOG_OWNER(HttpStatus.FORBIDDEN, "PL006", "로그 소유자가 아닙니다."),
	NOT_TEMP_PLACE_LOG(HttpStatus.BAD_REQUEST, "PL007", "해당 로그가 임시저장중이 아닙니다."),
	NOT_PUBLISHED_PLACE_LOG(HttpStatus.BAD_REQUEST, "PL008", "해당 로그가 퍼블리시중이 아닙니다"),
	NO_PLACE_LOG_NAME(HttpStatus.BAD_REQUEST, "PL009", "저장하려면 제목이 필요합니다"),

	// tag
	INVALID_TAG_CATEGORY(HttpStatus.BAD_REQUEST, "T001", "존재하지 않는 태그 카테고리 입니다."),
	TAG_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "T002", "태그는 최대 5개까지 등록 가능합니다."),
	INVALID_TAG(HttpStatus.BAD_REQUEST, "T003", "존재하지 않는 태그입니다.");



	private final HttpStatus status;
	private final String code;
	private final String message;
}