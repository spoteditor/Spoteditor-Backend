package com.spoteditor.backend.bookmark.service;

import com.spoteditor.backend.bookmark.repository.BookmarkRepository;
import com.spoteditor.backend.bookmark.service.dto.BookmarkCommand;
import com.spoteditor.backend.bookmark.service.facade.BookmarkFacade;
import com.spoteditor.backend.global.exception.PlaceException;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_PLACE;
import static com.spoteditor.backend.place.entity.Category.TOUR;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BookmarkServiceTest {

	@Autowired private PlaceRepository placeRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private BookmarkFacade bookmarkFacade;
	@Autowired private BookmarkRepository bookmarkRepository;

	@AfterEach
	void tearDown() {
		bookmarkRepository.deleteAll();
		placeRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("여러 쓰레드가 동시에 북마크 추가 요청시 락 획득에 성공한 쓰레드만 북마크를 추가한다")
	void 여러_쓰레드가_동시에_북마크_추가_요청시_락_획득에_성공한_쓰레드만_북마크를_추가한다() throws InterruptedException {
		// given
		Place place = createAndSavePlace();
		List<User> users = createAndSaveUsers(100);
		int threadCount = 100;

		// when
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		executeParallelBookmarkAdditions(executorService, latch, place, users);

		latch.await();

		// then
		Place findPlace = placeRepository.findById(place.getId())
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));
		assertThat(findPlace.getBookmark()).isEqualTo(100);
	}

	private void executeParallelBookmarkAdditions(
			ExecutorService executorService,
			CountDownLatch latch,
			Place place,
			List<User> users
	) {
		for (int i = 0; i < users.size(); i++) {
			final int index = i;
			executorService.submit(() -> {
				try {
					bookmarkFacade.addBookmark(users.get(index).getId(),
							new BookmarkCommand(place.getId()));
				} catch (Exception e) {
					// 예외 발생 시 무시
				} finally {
					latch.countDown();
				}
			});
		}
	}

	@Test
	@DisplayName("여러 쓰레드가 동시에 북마크 삭제 요청시 락 획득에 성공한 쓰레드만 북마크를 삭제한다")
	void 여러_쓰레드가_동시에_북마크_삭제_요청시_락_획득에_성공한_쓰레드만_북마크를_삭제한다() throws InterruptedException {
		// given
		Place place = createAndSavePlace();
		List<User> users = createAndSaveUsers(100);
		int threadCount = 100;

		// 테스트를 위한 초기 상태 설정: 모든 사용자가 북마크한 상태
		setupInitialBookmarks(place, users);

		// when
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		executeParallelBookmarkDeletions(executorService, latch, place, users);

		latch.await();

		// then
		Place findPlace = placeRepository.findById(place.getId())
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));
		assertThat(findPlace.getBookmark()).isEqualTo(0);
	}

	private void setupInitialBookmarks(Place place, List<User> users) {
		for (User user : users) {
			BookmarkCommand command = new BookmarkCommand(place.getId());
			bookmarkFacade.addBookmark(user.getId(), command);
		}
	}

	private void executeParallelBookmarkDeletions(
			ExecutorService executorService,
			CountDownLatch latch,
			Place place,
			List<User> users
	) {
		for (User user : users) {
			executorService.submit(() -> {
				try {
					bookmarkFacade.removeBookmark(user.getId(), new BookmarkCommand(place.getId()));
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}
	}

	private List<User> createAndSaveUsers(int count) {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			User user = User.builder()
					.email("test" + i + "@example.com")
					.name("test" + i)
					.build();
			users.add(user);
		}
		return userRepository.saveAll(users);
	}

	private Place createAndSavePlace() {
		User owner = User.builder()
				.email("owner@example.com")
				.name("owner")
				.build();
		userRepository.save(owner);

		Place place = Place.builder()
				.user(owner)
				.address(new Address("테스트",
						"테스트",
						37.1234,
						128.1234,
						"테스트",
						"테스트",
						"테스트"))
				.description("장소 설명")
				.name("장소 이름")
				.category(TOUR)
				.build();

		return placeRepository.save(place);
	}
}
