package com.spoteditor.backend.bookmark.controller;

import com.spoteditor.backend.bookmark.controller.dto.BookmarkRequest;
import com.spoteditor.backend.bookmark.service.facade.BookmarkFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookmarkApiController {

	private final BookmarkFacade bookmarkFacade;

	@PostMapping("/bookmark")
	public ResponseEntity<Void> addBookmark(@RequestBody BookmarkRequest bookmarkRequest) throws InterruptedException {

		bookmarkFacade.addBookmark(bookmarkRequest.from());
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}

	@DeleteMapping("/bookmark")
	public ResponseEntity<Void> removeBookmark(@RequestBody BookmarkRequest bookmarkRequest) throws InterruptedException {

		bookmarkFacade.removeBookmark(bookmarkRequest.from());
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
