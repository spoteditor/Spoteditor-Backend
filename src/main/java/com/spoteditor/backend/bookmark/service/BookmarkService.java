package com.spoteditor.backend.bookmark.service;

import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;

public interface BookmarkService {

	void addBookmark(BookmarkCommand command);
	void removeBookmark(BookmarkCommand command);
}
