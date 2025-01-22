package com.spoteditor.backend.domain.user.controller;

import com.spoteditor.backend.common.exceptions.BaseException;
import com.spoteditor.backend.common.exceptions.ErrorCode;
import com.spoteditor.backend.common.response.ApiResponse;
import com.spoteditor.backend.common.util.CookieUtil;
import com.spoteditor.backend.security.jwt.JwtConstants;
import com.spoteditor.backend.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtil jwtProvider;
    private final CookieUtil cookieUtil;

    @PostMapping("/refresh")
    public ApiResponse<?> refreshAccessToken (HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshToken = cookieUtil.getRefreshToken(request);

        if(refreshToken != null) {
            try {
                // RefreshToken 검증
                UsernamePasswordAuthenticationToken authentication = jwtProvider.setAuthentication(refreshToken);
                Long id = (Long) authentication.getPrincipal();

                // 검증 성공 -> accessToken 발급
                String accessToken = jwtProvider.createAccessToken(id);
                cookieUtil.addCookie(response, "/api", JwtConstants.ACCESS_TOKEN, accessToken);
            } catch (Exception e) {
                throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }
            return ApiResponse.ok(null);
        }
        throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}
