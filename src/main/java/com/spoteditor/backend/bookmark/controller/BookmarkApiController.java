package com.spoteditor.backend.bookmark.controller;

import com.spoteditor.backend.bookmark.controller.dto.BookmarkRequest;
import com.spoteditor.backend.bookmark.service.facade.BookmarkFacade;
import com.spoteditor.backend.config.swagger.BookmarkApiDocument;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "bookmark", description = "북마크 API")
public class BookmarkApiController implements BookmarkApiDocument {

	private final BookmarkFacade bookmarkFacade;

	@PostMapping("/bookmark")
	public ResponseEntity<Void> addBookmark(@AuthenticationPrincipal UserIdDto dto,
											@RequestBody BookmarkRequest bookmarkRequest) throws InterruptedException {

		bookmarkFacade.addBookmark(dto.getId(), bookmarkRequest.toCommandDto());	// 컨트롤러 DTO를 서비스 DTO로 명확하게 변환하는 네이밍으로 수정
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}

	/**
	 *
	 * @param dto
	 * @param bookmarkRequest
	 * @return
	 * @throws InterruptedException
	 */
	@DeleteMapping("/bookmark")
	public ResponseEntity<Void> removeBookmark(@AuthenticationPrincipal UserIdDto dto,
											   @RequestBody BookmarkRequest bookmarkRequest) throws InterruptedException {

		bookmarkFacade.removeBookmark(dto.getId(), bookmarkRequest.toCommandDto());
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
