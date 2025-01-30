package com.spoteditor.backend.domain.user.service;

import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import com.spoteditor.backend.common.exceptions.user.UserException;
import com.spoteditor.backend.common.util.CookieUtils;
import com.spoteditor.backend.domain.user.common.dto.UserIdDto;
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
            throw new UserException(UserErrorCode.REFRESH_TOKEN_NOT_IN_COOKIE);
        }

        try {
            // RefreshToken 검증
            UsernamePasswordAuthenticationToken authentication = jwtUtils.setAuthentication(refreshToken);
            UserIdDto userIdDto = (UserIdDto) authentication.getPrincipal();

            // 검증 성공 -> accessToken 발급
            String accessToken = jwtUtils.createAccessToken(userIdDto.getId());
            cookieUtils.setAccessTokenCookie(response, JwtConstants.ACCESS_TOKEN, accessToken);
        } catch (UserException e) {
            throw new UserException(UserErrorCode.REFRESH_TOKEN_INVALID);
        }
    }
}
