package com.spoteditor.backend.domain.user.controller;

import com.spoteditor.backend.common.exceptions.BaseException;
import com.spoteditor.backend.common.exceptions.ErrorCode;
import com.spoteditor.backend.common.util.CookieUtils;
import com.spoteditor.backend.security.jwt.JwtConstants;
import com.spoteditor.backend.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    @PostMapping("/refresh")
    public ResponseEntity<List> refreshAccessToken (HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshToken = cookieUtils.getRefreshToken(request);

        if(refreshToken != null) {
            try {
                // RefreshToken 검증
                UsernamePasswordAuthenticationToken authentication = jwtUtils.setAuthentication(refreshToken);
                Long id = (Long) authentication.getPrincipal();

                // 검증 성공 -> accessToken 발급
                String accessToken = jwtUtils.createAccessToken(id);
                cookieUtils.addCookie(response, "/api", JwtConstants.ACCESS_TOKEN, accessToken);
            } catch (Exception e) {
                throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}
