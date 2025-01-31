package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.config.page.PageRequest;
import com.spoteditor.backend.config.page.PageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spoteditor.backend.place.entity.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PlaceRepositoryTest {

	@Autowired private PlaceRepository placeRepository;
	@Autowired private UserRepository userRepository;

	@Test
	@DisplayName("공간을 조회할 수 있다.")
	void 공간을_조회할_수_있다() {

		// given
		User user = userRepository.save(User.builder()
				.email("test@example.com")
				.name("아무개")
				.build());

		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setSize(20);
		pageRequest.setDirection(Sort.Direction.ASC);

		Place place1 = Place.builder()
				.user(user)
				.address(Address.builder()
						.address("테스트1")
						.roadAddress("테스트1")
						.latitude(37.123)
						.longitude(128.123)
						.sido("테스트1")
						.bname("테스트1")
						.sigungu("테스트1")
						.build())
				.description("장소 설명1")
				.name("장소 이름1")
				.category(TOUR)
				.build();

		Place place2 = Place.builder()
				.user(user)
				.address(Address.builder()
						.address("테스트2")
						.roadAddress("테스트2")
						.latitude(37.1234)
						.longitude(128.1234)
						.sido("테스트2")
						.bname("테스트2")
						.sigungu("테스트2")
						.build())
				.description("장소 설명2")
				.name("장소 이름2")
				.category(CAFE)
				.build();

		Place place3 = Place.builder()
				.user(user)
				.address(Address.builder()
						.address("테스트3")
						.roadAddress("테스트3")
						.latitude(37.12345)
						.longitude(128.12345)
						.sido("테스트3")
						.bname("테스트3")
						.sigungu("테스트3")
						.build())
				.description("장소 설명3")
				.name("장소 이름3")
				.category(RESTAURANT)
				.build();
		placeRepository.saveAll(List.of(place1, place2, place3));

		// when
		PageResponse<PlaceResponse> places = placeRepository.findAllPlace(pageRequest);
		List<PlaceResponse> content = places.getContent();

		// then
		assertThat(content).hasSize(3)
				.extracting("name", "description", "category")
				.containsExactlyInAnyOrder(
						tuple("장소 이름1", "장소 설명1", TOUR),
						tuple("장소 이름2", "장소 설명2", CAFE),
						tuple("장소 이름3", "장소 설명3", RESTAURANT)
				);
	}
}