package com.spoteditor.backend.security.oauth.handler;

import com.spoteditor.backend.config.util.CookieUtils;
import com.spoteditor.backend.config.jwt.JwtConstants;
import com.spoteditor.backend.config.jwt.JwtUtils;
import com.spoteditor.backend.config.oauth.handler.OauthSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.mockito.Mockito.*;

class OauthSuccessHandlerTest {

    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;
    private OauthSuccessHandler successHandler;
    private Authentication authentication;
    private OAuth2User oauth2User;

    @BeforeEach
    void setUp() {
        jwtUtils = mock(JwtUtils.class);
        cookieUtils = mock(CookieUtils.class);
        successHandler = new OauthSuccessHandler(jwtUtils, cookieUtils);
        authentication = mock(Authentication.class);
        oauth2User = mock(OAuth2User.class);
    }

    @Test
    @DisplayName("JWT와 쿠키가 정상적으로 생성 및 추가되는지 확인")
    void 쿠키정보_가져오기() throws Exception {
        // given
        HttpServletResponse response = mock(HttpServletResponse.class);
        Map<String, Object> attributes = Map.of(
                "id", 123L
        );

        when(authentication.getPrincipal()).thenReturn(oauth2User);
        when(oauth2User.getAttributes()).thenReturn(attributes);
        when(jwtUtils.createAccessToken(123L)).thenReturn("mockAccessToken");
        when(jwtUtils.createRefreshToken(123L)).thenReturn("mockRefreshToken");

        // when
        successHandler.onAuthenticationSuccess(null, response, authentication);

        // then
        verify(jwtUtils).createAccessToken(123L);
        verify(jwtUtils).createRefreshToken(123L);

        verify(cookieUtils).setAccessTokenCookie(response, JwtConstants.ACCESS_TOKEN, "mockAccessToken");
        verify(cookieUtils).setRefreshTokenCookie(response, JwtConstants.REFRESH_TOKEN, "mockRefreshToken");
    }
}