package com.spoteditor.backend.place.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoteditor.backend.config.page.PageRequest;
import com.spoteditor.backend.config.page.PageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.PlaceService;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.spoteditor.backend.place.entity.Category.TOUR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlaceApiController.class)
class PlaceApiControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;	// 직렬화 / 역직렬화위한 매핑

	@MockitoBean private PlaceService placeService;
	@MockitoBean private PlaceRepository placeRepository;

	@Test
	@DisplayName("공간을 추가한다.")
	void 공간을_추가한다() throws Exception {

		// given
		Long userId = 1L;

		PlaceRegisterRequest request = PlaceRegisterRequest.builder()
				.name("공간1")
				.description("공간1")
				.originalFile("원본 파일")
				.address(Address.builder()
						.address("테스트1")
						.roadAddress("테스트1")
						.latitude(37.123)
						.longitude(128.123)
						.sido("테스트1")
						.bname("테스트1")
						.sigungu("테스트1")
						.build())
				.category(TOUR)
				.build();

		PlaceRegisterResult mockResult = new PlaceRegisterResult(
				userId,
				1L,
				request.name(),
				request.description(),
				0,
				request.address(),
				request.category(),
				List.of()
		);

		when(placeService.addPlace(eq(userId), any(PlaceRegisterCommand.class)))
				.thenReturn(mockResult);

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/places")
				.param("userId", String.valueOf(userId))
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("공간을 조회한다.")
	void 공간을_조회한다() throws Exception {

		// given
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setSize(20);
		pageRequest.setDirection(Sort.Direction.ASC);

		List<PlaceResponse> content = new ArrayList<>();
		org.springframework.data.domain.PageRequest springPageRequest = org.springframework.data.domain.PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id"));
		Page<PlaceResponse> page = new PageImpl<>(content, springPageRequest, 0);
		PageResponse<PlaceResponse> result = new PageResponse<>(page);

		when(placeRepository.findAllPlace(any(PageRequest.class))).thenReturn(result);

		// when & then
		mockMvc.perform(
						get("/api/places")
								.param("page", "1")
								.param("size", "20")
								.param("direction", "ASC")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content").exists())
				.andExpect(jsonPath("$.content").isEmpty());
	}
}