package com.spoteditor.backend.security.jwt;

import com.spoteditor.backend.config.jwt.JwtProperties;
import com.spoteditor.backend.config.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
class JwtUtilTest {

    @Autowired private JwtUtils jwtUtils;
    @Autowired private JwtProperties jwtProperties;

    @Test
    @DisplayName("AccessToken 생성 후 claim 확인")
    public void AccessToken_생성_테스트() throws Exception {
        // given
        Long userId = 24L;

        String token = jwtUtils.createAccessToken(userId);
        assertNotNull(token);

        // when
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(jwtProperties.getSigningKey())
                .build()
                .parseSignedClaims(token);

        String uid = (String) claims.getPayload().get("sub");

        // then
        assertEquals(userId.toString(), uid);
    }

    @Test
    @DisplayName("RefreshToken 생성 후 claim 확인")
    public void RefreashToken_생성_테스트() throws Exception {
        // given
        Long userId = 25L;

        String token = jwtUtils.createRefreshToken(userId);
        assertNotNull(token);

        // when
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(jwtProperties.getSigningKey())
                .build()
                .parseSignedClaims(token);

        String uid = (String) claims.getPayload().get("sub");

        // then
        assertEquals(userId.toString(), uid);
    }

    @Test
    @DisplayName("토큰 만료시 ExpiredJwtException 예외")
    public void 토큰_만료_테스트() throws Exception {
        // given
        String token = jwtUtils.createToken(10L, -100);
        assertNotNull(token);

        // when & then
        Assertions.assertThrows(ExpiredJwtException.class, () -> jwtUtils.setAuthentication(token));
    }

    @Test
    @DisplayName("유효하지 않은 토큰 Exception 예외")
    public void 유효하지않은_토큰_테스트() throws Exception {
        // given

        // when & then
        Assertions.assertThrows(Exception.class, () -> jwtUtils.setAuthentication("qwer"));
    }

    @Test
    @DisplayName("인증완료시 인증객체에서 uid 확인")
    public void 인증완료_테스트() throws Exception {
        // given
        Long id = 10L;
        String token = jwtUtils.createAccessToken(id);

        // when
        UsernamePasswordAuthenticationToken authenticationToken = jwtUtils.setAuthentication(token);

        // then
        assertEquals(authenticationToken.getPrincipal(), id.toString());
    }
}