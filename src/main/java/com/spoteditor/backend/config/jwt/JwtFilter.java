package com.spoteditor.backend.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spoteditor.backend.config.util.CookieUtils;
import com.spoteditor.backend.global.exception.TokenException;
import com.spoteditor.backend.global.response.ErrorCode;
import com.spoteditor.backend.global.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
import java.util.List;

import static com.spoteditor.backend.global.response.ErrorCode.INVALID_ACCESS_TOKEN;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    private static final List<String> WHITE_LIST = List.of(
        "/favicon.ico",
        "/error",
        "/api/health",
        "/api/docs/**",
        "/v3/api-docs/**",
        "/swagger-ui/**"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return WHITE_LIST.stream()
                .anyMatch(pattern -> {
                    if (pattern.endsWith("/**")) {
                        String prefix = pattern.substring(0, pattern.length() - 3);
                        return path.startsWith(prefix);
                    }
                    return path.equals(pattern);
                });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        // /favicon.ico 요청은 필터를 건너뜀
        if ("/favicon.ico".equals(path) || "/api/auth/refresh".equals(path)) {
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

            filterChain.doFilter(request, response);
        } catch(ExpiredJwtException | IllegalArgumentException | MalformedJwtException | SignatureException e) {
            handleException(response, new TokenException(INVALID_ACCESS_TOKEN));
        }
    }
    private void handleException(HttpServletResponse response, TokenException tokenException) throws IOException {
        ErrorCode errorCode = tokenException.getErrorCode();

        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonResponse = objectMapper.writeValueAsString(ErrorResponse.of(errorCode));
        response.getWriter().write(jsonResponse);
    }
}

