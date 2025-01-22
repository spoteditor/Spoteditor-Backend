package com.spoteditor.backend.security.oauth.handler;

import com.spoteditor.backend.security.jwt.JwtProvider;
import com.spoteditor.backend.security.oauth.dto.OauthAttributes;
import com.spoteditor.backend.security.oauth.service.OauthUserResolver;
import jakarta.servlet.FilterChain;
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

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributesMap = oauthUser.getAttributes();

        Long id = (Long) attributesMap.get("id");
        String name = (String) attributesMap.get("name");

        String accessToken = jwtProvider.createAccessToken(id, name);
        String refreshToken = jwtProvider.createRefreshToken(id);

//        redirect
    }
}
