package com.spoteditor.backend.bookmark.service.facade;

import com.spoteditor.backend.bookmark.service.BookmarkService;
import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkFacade {

	private final BookmarkService bookmarkService;

	public void addBookmark(BookmarkCommand command) throws InterruptedException {

		while (true) {
			try {
				bookmarkService.addBookmark(command);
				break;
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
	}

	public void removeBookmark(BookmarkCommand command) throws InterruptedException {

		while (true) {
			try {
				bookmarkService.removeBookmark(command);
				break;
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
	}
}
