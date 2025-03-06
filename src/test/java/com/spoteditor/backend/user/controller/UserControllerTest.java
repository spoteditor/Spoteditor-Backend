package com.spoteditor.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoteditor.backend.config.TestSecurityConfig;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.service.UserService;
import com.spoteditor.backend.user.service.dto.UserResult;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import com.spoteditor.backend.user.service.dto.UserUpdateResult;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("현재 사용자 정보 조회 API 테스트")
    void 현재_사용자_정보_조회_테스트() throws Exception {
        // Given
        Long userId = 1L;

        User user = User.builder()
                .email("test@email.com")
                .build();

        UserUpdateCommand command = new UserUpdateCommand(
                "test",
                "test.jpg",
                "테스트 유저입니다",
                "test_test"
        );

        user.update(command);

        ReflectionTestUtils.setField(user, "id", userId);

        UserResult userResult = UserResult.from(user, 99L, 123L);

        given(userService.getUser(userId)).willReturn(userResult);

        // When & Then
        mockMvc.perform(get("/api/users")
                        .cookie(new Cookie("AccessToken", String.valueOf(userId)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.instagramId").value(user.getInstagramId()))
                .andExpect(jsonPath("$.imageUrl").value(user.getImageUrl()))
                .andExpect(jsonPath("$.description").value(user.getDescription()))
                .andExpect(jsonPath("$.follower").value(userResult.follower()))
                .andExpect(jsonPath("$.following").value(userResult.following()));
    }
}
