package com.spoteditor.backend.domain.user.service;

import com.spoteditor.backend.common.util.CookieUtils;
import com.spoteditor.backend.security.jwt.JwtConstants;
import com.spoteditor.backend.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshToken = cookieUtils.getRefreshToken(request);

        if(refreshToken == null) {
            throw new Exception("쿠키에 RefreshToken 없음");
        }

        try {
            // RefreshToken 검증
            UsernamePasswordAuthenticationToken authentication = jwtUtils.setAuthentication(refreshToken);
            Long id = (Long) authentication.getPrincipal();

            // 검증 성공 -> accessToken 발급
            String accessToken = jwtUtils.createAccessToken(id);
            cookieUtils.addCookie(response, "/api", JwtConstants.ACCESS_TOKEN, accessToken);
        } catch (Exception e) {
            throw new Exception("유효하지 않은 RefreshToken");
        }
    }
}
