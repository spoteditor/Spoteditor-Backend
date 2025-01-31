package com.spoteditor.backend.place.service;

import com.spoteditor.backend.config.AwsS3Config;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.spoteditor.backend.place.entity.Category.TOUR;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(AwsS3Config.class)
@Transactional
class PlaceServiceTest {

	@Autowired private UserRepository userRepository;
	@Autowired private PlaceService placeService;

	@Test
	@DisplayName("사용자는 공간을 직접 등록할 수 있고 추가로 공간에 대한 사진도 추가할 수 있다.")
	void 사용자는_공간을_직접_등록할_수_있고_추가로_공간에_대한_사진도_추가할_수_있다() {

		// given
		User user1 = createUser("test1@example.com", "아무개1");
		userRepository.save(user1);

		PlaceRegisterCommand command1 = new PlaceRegisterCommand("공간 추가1",
				"공간을 추가해보려고 해요1",
				"원본 파일1",
				Address.builder()
						.address("테스트1")
						.roadAddress("테스트1")
						.latitude(37.123)
						.longitude(128.123)
						.sido("테스트1")
						.bname("테스트1")
						.sigungu("테스트1")
						.build(),
				TOUR);

		// when
		PlaceRegisterResult result = placeService.addPlace(user1.getId(), command1);;

		// then
		assertThat(result.userId()).isEqualTo(user1.getId());
		assertThat(result.placeId()).isNotNull();
		assertThat(result)
				.extracting("name", "description")
				.contains("공간 추가1", "공간을 추가해보려고 해요1");
		assertThat(result.images())
				.hasSize(1)
				.extracting("originalFile")
				.contains("원본 파일1");
	}

	private User createUser(String email, String name) {
		return User.builder()
				.email(email)
				.name(name)
				.build();
	}
}