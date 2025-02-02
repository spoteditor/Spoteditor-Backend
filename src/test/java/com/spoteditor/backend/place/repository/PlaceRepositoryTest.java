package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
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

		CustomPageRequest pageRequest = new CustomPageRequest();
		pageRequest.setPage(1);
		pageRequest.setSize(20);
		pageRequest.setDirection(Sort.Direction.ASC);

		Place place1 = Place.builder()
				.user(user)
				.address(new Address("테스트1",
						"테스트1",
						37.123,
						128.123,
						"테스트1",
						"테스트1",
						"테스트1"))
				.description("장소 설명1")
				.name("장소 이름1")
				.category(TOUR)
				.build();

		Place place2 = Place.builder()
				.user(user)
				.address(new Address("테스트2",
						"테스트2",
						37.1234,
						128.1234,
						"테스트2",
						"테스트2",
						"테스트2"))
				.description("장소 설명2")
				.name("장소 이름2")
				.category(CAFE)
				.build();

		Place place3 = Place.builder()
				.user(user)
				.address(new Address("테스트3",
						"테스트3",
						37.12345,
						128.12345,
						"테스트3",
						"테스트3",
						"테스트3"))
				.description("장소 설명3")
				.name("장소 이름3")
				.category(RESTAURANT)
				.build();
		placeRepository.saveAll(List.of(place1, place2, place3));

		// when
		CustomPageResponse<PlaceResponse> places = placeRepository.findAllPlace(pageRequest);
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