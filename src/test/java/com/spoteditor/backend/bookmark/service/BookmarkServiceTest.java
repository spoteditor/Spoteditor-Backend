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
	@DisplayName("여러 쓰레드가 동시에 북마크 추가 요청시 쓰레드 개수만큼 북마크 개수 역시 증가해야 한다")
	void addBookmarkConcurrencyTest() throws InterruptedException {
		// given
		Place place = createAndSavePlace();
		List<User> users = createAndSaveUsers(100);

		// when
		int threadCount = 100;
		executeMultiThreadTest(threadCount, users, place.getId(), true);

		// then
		Place findPlace = placeRepository.findById(place.getId()).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));
		assertThat(findPlace.getBookmark()).isEqualTo(threadCount);
	}

	@Test
	@DisplayName("여러 쓰레드가 동시에 북마크 삭제 요청시 쓰레드 개수만큼 북마크 개수 역시 감소해야 한다")
	void removeBookmarkConcurrencyTest() throws InterruptedException {
		// given
		List<User> users = createAndSaveUsers(100);
		Place place = createAndSavePlace();

		int threadCount = 100;
		executeMultiThreadTest(threadCount, users, place.getId(), true);

		Place placeAfterAdd = placeRepository.findById(place.getId()).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));
		assertThat(placeAfterAdd.getBookmark()).isEqualTo(threadCount);

		// when
		executeMultiThreadTest(threadCount, users, place.getId(), false);

		// then
		Place findPlace = placeRepository.findById(place.getId()).orElseThrow();
		assertThat(findPlace.getBookmark()).isEqualTo(0);
	}

	private void executeMultiThreadTest(int threadCount, List<User> users, Long placeId, boolean isAdd) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			final int index = i;
			executorService.submit(() -> {
				try {
					BookmarkCommand command = new BookmarkCommand(users.get(index).getId(), placeId);
					if (isAdd) {
						bookmarkFacade.addBookmark(command);
					} else {
						bookmarkFacade.removeBookmark(command);
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();
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
				.address(Address.builder()
						.address("테스트주소")
						.roadAddress("테스트도로주소")
						.latitude(37.123)
						.longitude(128.123)
						.sido("테스트시도")
						.bname("테스트법정동")
						.sigungu("테스트시군구")
						.build())
				.description("장소 설명")
				.name("장소 이름")
				.category(TOUR)
				.build();

		return placeRepository.save(place);
	}
}
