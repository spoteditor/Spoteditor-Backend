package com.spoteditor.backend.bookmark.service;

import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;

public interface BookmarkService {

	void addBookmark(Long userId, BookmarkCommand command);
	void removeBookmark(Long userId, BookmarkCommand command);
}
