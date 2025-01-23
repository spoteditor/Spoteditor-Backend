package com.spoteditor.backend.bookmark.service;

import com.spoteditor.backend.bookmark.entity.Bookmark;
import com.spoteditor.backend.bookmark.repository.BookmarkRepository;
import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;
import com.spoteditor.backend.global.exception.BookmarkException;
import com.spoteditor.backend.global.exception.PlaceException;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.user.User;
import com.spoteditor.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spoteditor.backend.global.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final PlaceRepository placeRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public void addBookmark(BookmarkCommand command) {

		User user = userRepository.findById(command.userId())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		Place place = placeRepository.findByIdWithOptimisticLock(command.placeId())
				.orElseThrow(() -> new BookmarkException(NOT_FOUND_PLACE));

		Bookmark bookmark = Bookmark.builder()
				.user(user)
				.place(place)
				.build();

		place.increaseBookmark();
		bookmarkRepository.saveAndFlush(bookmark);
	}

	@Override
	@Transactional
	public void removeBookmark(BookmarkCommand command) {

		Place place = placeRepository.findByIdWithOptimisticLock(command.placeId())
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));

		Bookmark bookmark = bookmarkRepository.findByUserIdAndPlaceId(command.userId(), command.placeId())
				.orElseThrow(() -> new BookmarkException(NOT_FOUND_BOOKMARK));

		place.decreaseBookmark();
		bookmarkRepository.delete(bookmark);
	}
}
