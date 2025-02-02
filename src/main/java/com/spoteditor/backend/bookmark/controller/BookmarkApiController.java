package com.spoteditor.backend.bookmark.controller;

import com.spoteditor.backend.bookmark.controller.dto.BookmarkRequest;
import com.spoteditor.backend.bookmark.service.facade.BookmarkFacade;
import com.spoteditor.backend.user.common.dto.UserIdDto;
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
public class BookmarkApiController {

	private final BookmarkFacade bookmarkFacade;

	@PostMapping("/bookmark")
	public ResponseEntity<Void> addBookmark(@AuthenticationPrincipal UserIdDto dto,
											@RequestBody BookmarkRequest bookmarkRequest) throws InterruptedException {

		bookmarkFacade.addBookmark(dto.getId(), bookmarkRequest.from());
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}

	@DeleteMapping("/bookmark")
	public ResponseEntity<Void> removeBookmark(@AuthenticationPrincipal UserIdDto dto,
											   @RequestBody BookmarkRequest bookmarkRequest) throws InterruptedException {

		bookmarkFacade.removeBookmark(dto.getId(), bookmarkRequest.from());
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
