package com.spoteditor.backend.place.service;

import com.spoteditor.backend.config.AwsS3Config;
import com.spoteditor.backend.image.service.PlaceImageService;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.spoteditor.backend.place.entity.Category.TOUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Import(AwsS3Config.class)
@Transactional
class PlaceServiceTest {

	@Autowired private UserRepository userRepository;
	@Autowired private PlaceService placeService;
	@Autowired private PlaceImageService placeImageService;

	private RedisTemplate<String, String> redisTemplate;

	@BeforeEach
	void setUp() {
		redisTemplate = mock(RedisTemplate.class);
		ValueOperations<String, String> valueOps = mock(ValueOperations.class);

		when(redisTemplate.opsForValue()).thenReturn(valueOps);
		when(valueOps.get(anyString())).thenReturn("mocked_temp_path");

		ReflectionTestUtils.setField(placeImageService, "redisTemplate", redisTemplate);
	}

	@Test
	@DisplayName("사용자는 공간을 직접 등록할 수 있고 추가로 공간에 대한 사진도 추가할 수 있다.")
	void 사용자는_공간을_직접_등록할_수_있고_추가로_공간에_대한_사진도_추가할_수_있다() {

		// given
		User user1 = createUser("test1@example.com", "아무개1");
		userRepository.save(user1);

		String uuid = UUID.randomUUID().toString();

		Address address = new Address("테스트",
				"테스트",
				37.1234,
				128.1234,
				"테스트",
				"테스트",
				"테스트");

		PlaceRegisterCommand command1 = new PlaceRegisterCommand("공간 추가1",
				"공간을 추가해보려고 해요1",
				"원본 파일1",
				uuid,
				address,
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