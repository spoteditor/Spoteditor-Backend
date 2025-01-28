package com.spoteditor.backend.security.jwt;

import com.spoteditor.backend.common.exceptions.user.UserException;
import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import com.spoteditor.backend.common.util.CookieUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        // /favicon.ico 요청은 필터를 건너뜀
        if ("/favicon.ico".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 쿠키에서 jwt 추출
        String accessToken = cookieUtils.getAccessToken(request);
        try {
            // 토큰 인증
            UsernamePasswordAuthenticationToken authentication = jwtUtils.setAuthentication(accessToken);

            // 인증정보 SecurityContextHolder 에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(ExpiredJwtException e) {
            // AccessToken 만료 -> RefreshToken 으로 재발급
            throw new UserException(UserErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            // TODO 유효하지 않은 토큰 -> 재로그인?
            throw new UserException(UserErrorCode.INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);
    }
}
