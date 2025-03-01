package com.spoteditor.backend.bookmark.service.facade;

import com.spoteditor.backend.bookmark.aop.DistributedLock;
import com.spoteditor.backend.bookmark.service.BookmarkService;
import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookmarkFacade {

	private final BookmarkService bookmarkService;

	@DistributedLock(key = "#command.placeId()")
	public void addBookmark(Long userId, BookmarkCommand command) {
		bookmarkService.addBookmark(userId, command);
	}

	public void removeBookmark(Long userId, BookmarkCommand command) {
		bookmarkService.removeBookmark(userId, command);
	}
}
