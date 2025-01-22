package com.spoteditor.backend.security.jwt;

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

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // jwt 추출
        String header = request.getHeader(JwtConstants.TOKEN_HEADER);
        if(header == null || header.isEmpty() || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = header.replace(JwtConstants.TOKEN_PREFIX, "");

        try {
            // 토큰 인증
            UsernamePasswordAuthenticationToken authentication = jwtProvider.setAuthentication(jwt);

            // 인증정보 SecurityContextHolder 에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(ExpiredJwtException e) {
            // TODO 만료된 토큰
            return;
        } catch (Exception e) {
            // TODO 예외처리
            return;
        }
        filterChain.doFilter(request, response);
    }
}
