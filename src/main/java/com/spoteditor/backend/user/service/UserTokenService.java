package com.spoteditor.backend.user.service;

import com.spoteditor.backend.config.util.CookieUtils;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.user.common.dto.UserTokenDto;
import com.spoteditor.backend.config.jwt.JwtConstants;
import com.spoteditor.backend.config.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.spoteditor.backend.global.response.ErrorCode.REFRESH_TOKEN_INVALID;
import static com.spoteditor.backend.global.response.ErrorCode.REFRESH_TOKEN_NOT_IN_COOKIE;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshToken = cookieUtils.getRefreshToken(request);

        if(refreshToken == null) {
            throw new UserException(REFRESH_TOKEN_NOT_IN_COOKIE);
        }

        try {
            // RefreshToken 검증
            UsernamePasswordAuthenticationToken authentication = jwtUtils.setAuthentication(refreshToken);
            UserTokenDto userIdDto = (UserTokenDto) authentication.getPrincipal();

            // 검증 성공 -> accessToken 발급
            String accessToken = jwtUtils.createAccessToken(userIdDto.getId(), userIdDto.getRole());
            cookieUtils.setAccessTokenCookie(response, JwtConstants.ACCESS_TOKEN, accessToken);
        } catch (UserException e) {
            throw new UserException(REFRESH_TOKEN_INVALID);
        }
    }
}
