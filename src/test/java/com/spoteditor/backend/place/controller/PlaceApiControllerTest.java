package com.spoteditor.backend.place.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.PlaceService;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.user.common.dto.UserTokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.spoteditor.backend.place.entity.Category.TOUR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlaceApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlaceApiControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockitoBean private PlaceService placeService;
	@MockitoBean private PlaceRepository placeRepository;

	@BeforeEach
	void setUp() {
		UserTokenDto principal = new UserTokenDto(1L);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				principal,
				"",
				Collections.emptyList()
		);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	@Test
	@DisplayName("공간을 추가한다.")
	void 공간을_추가한다() throws Exception {

		// given
		Long userId = 1L;

		PlaceRegisterRequest request = PlaceRegisterRequest.builder()
				.name("공간1")
				.description("공간1")
				.originalFile("원본 파일")
				.address(new Address("테스트",
						"테스트",
						37.1234,
						128.1234,
						"테스트",
						"테스트",
						"테스트"))
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
		CustomPageRequest pageRequest = new CustomPageRequest();
		pageRequest.setPage(1);
		pageRequest.setSize(20);
		pageRequest.setDirection(Sort.Direction.ASC);

		List<PlaceResponse> content = new ArrayList<>();
		org.springframework.data.domain.PageRequest springPageRequest = org.springframework.data.domain.PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id"));
		Page<PlaceResponse> page = new PageImpl<>(content, springPageRequest, 0);
		CustomPageResponse<PlaceResponse> result = new CustomPageResponse<>(page);

		when(placeRepository.findAllPlace(any(CustomPageRequest.class))).thenReturn(result);

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