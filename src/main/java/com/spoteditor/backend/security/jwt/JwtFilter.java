package com.spoteditor.backend.security.jwt;

import com.spoteditor.backend.common.exceptions.BaseException;
import com.spoteditor.backend.common.exceptions.ErrorCode;
import com.spoteditor.backend.common.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtProvider;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에서 jwt 추출
        String accessToken = cookieUtil.getAccessToken(request);

        try {
            // 토큰 인증
            UsernamePasswordAuthenticationToken authentication = jwtProvider.setAuthentication(accessToken);

            // 인증정보 SecurityContextHolder 에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(ExpiredJwtException e) {
            // AccessToken 만료 -> RefreshToken 으로 재발급
            throw new BaseException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            // TODO 유효하지 않은 토큰 -> 재로그인?
            throw new BaseException(ErrorCode.INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);
    }
}
