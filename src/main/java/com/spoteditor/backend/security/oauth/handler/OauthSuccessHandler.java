package com.spoteditor.backend.security.oauth.handler;

import com.spoteditor.backend.common.util.CookieUtil;
import com.spoteditor.backend.security.jwt.JwtConstants;
import com.spoteditor.backend.security.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtProvider;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributesMap = oauthUser.getAttributes();

        Long id = (Long) attributesMap.get("id");
        String name = (String) attributesMap.get("name");

        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshToken(id);

        cookieUtil.addCookie(response, "/api", JwtConstants.ACCESS_TOKEN, accessToken);
        cookieUtil.addCookie(response, "/auth", JwtConstants.REFRESH_TOKEN, refreshToken);

//        redirect
    }
}
