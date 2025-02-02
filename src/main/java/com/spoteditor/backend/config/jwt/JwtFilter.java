package com.spoteditor.backend.config.jwt;

import com.spoteditor.backend.config.util.CookieUtils;
import com.spoteditor.backend.global.exception.UserException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.spoteditor.backend.global.response.ErrorCode.ACCESS_TOKEN_EXPIRED;
import static com.spoteditor.backend.global.response.ErrorCode.INVALID_TOKEN;

@RequiredArgsConstructor
@Slf4j
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
        log.info(accessToken);

        try {
            // 토큰 인증
            UsernamePasswordAuthenticationToken authentication = jwtUtils.setAuthentication(accessToken);

            // 인증정보 SecurityContextHolder 에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(ExpiredJwtException e) {
            throw new UserException(ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new UserException(INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);
    }
}
