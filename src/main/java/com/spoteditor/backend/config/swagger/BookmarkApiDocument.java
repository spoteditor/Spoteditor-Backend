package com.spoteditor.backend.config.swagger;

import com.spoteditor.backend.bookmark.controller.dto.BookmarkRequest;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface BookmarkApiDocument {

	@Operation(summary = "북마크 추가", description = "장소에 대한 북마크를 추가합니다.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "북마크 추가 성공",
					content = @Content(schema = @Schema(implementation = Void.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "잘못된 요청",
					content = @Content
			),
			@ApiResponse(
					responseCode = "401",
					description = "인증되지 않은 사용자",
					content = @Content
			),
			@ApiResponse(
					responseCode = "404",
					description = "존재하지 않는 장소",
					content = @Content
			)
	})
	ResponseEntity<Void> addBookmark(
			@Parameter(description = "인증된 사용자 정보", required = true) UserIdDto dto,
			@Parameter(description = "북마크 추가 요청 정보", required = true) BookmarkRequest bookmarkRequest
	) throws InterruptedException;

	@Operation(summary = "북마크 삭제", description = "장소에 대한 북마크를 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "204",
					description = "북마크 삭제 성공",
					content = @Content(schema = @Schema(implementation = Void.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "잘못된 요청",
					content = @Content
			),
			@ApiResponse(
					responseCode = "401",
					description = "인증되지 않은 사용자",
					content = @Content
			),
			@ApiResponse(
					responseCode = "404",
					description = "존재하지 않는 북마크",
					content = @Content
			)
	})
	ResponseEntity<Void> removeBookmark(
			@Parameter(description = "인증된 사용자 정보", required = true) UserIdDto dto,
			@Parameter(description = "북마크 삭제 요청 정보", required = true) BookmarkRequest bookmarkRequest
	) throws InterruptedException;
}