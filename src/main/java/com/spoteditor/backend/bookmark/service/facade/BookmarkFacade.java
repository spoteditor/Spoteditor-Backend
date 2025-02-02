package com.spoteditor.backend.bookmark.service.facade;

import com.spoteditor.backend.bookmark.service.BookmarkService;
import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;
import com.spoteditor.backend.global.exception.BookmarkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.spoteditor.backend.global.response.ErrorCode.BOOKMARK_PROCESSING_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookmarkFacade {

	private final BookmarkService bookmarkService;
	private final RedissonClient redissonClient;

	public void addBookmark(Long userId, BookmarkCommand command) {

		RLock lock = redissonClient.getLock(command.placeId().toString());

		try {
			boolean b = lock.tryLock(10, 1, TimeUnit.SECONDS);

			if (!b) {
				log.warn("락 획득 실패");
				return;
			}
			bookmarkService.addBookmark(userId, command);
		} catch (InterruptedException e) {
			throw new BookmarkException(BOOKMARK_PROCESSING_FAILED);
		} finally {
			lock.unlock();
		}
	}

	public void removeBookmark(Long userId, BookmarkCommand command) throws InterruptedException {

		RLock lock = redissonClient.getLock(command.placeId().toString());

		try {
			boolean b = lock.tryLock(10, 1, TimeUnit.SECONDS);

			if (!b) {
				log.warn("락 획득 실패");
				return;
			}
			bookmarkService.removeBookmark(userId, command);
		} catch (InterruptedException e) {
			throw new BookmarkException(BOOKMARK_PROCESSING_FAILED);
		} finally {
			lock.unlock();
		}
	}
}
