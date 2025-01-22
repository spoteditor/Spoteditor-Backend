package com.spoteditor.backend.security.jwt;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtProvider {

    private final JwtProps jwtProps;

    public String createAccessToken(Long id, String name) {
        SecretKey signingKey = jwtProps.getSigningKey();

        return Jwts.builder()
                .signWith(signingKey, JwtConstants.ALGORITHM)
                .header()
                .add("typ", JwtConstants.TOKEN_TYPE)
                .and()
                .claim("sub", id)
                .claim("name", name)
                .claim("iat", new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProps.getAccessTokenExp()))
                .compact();
    }

    public String createRefreshToken(Long id) {
        SecretKey signingKey = jwtProps.getSigningKey();

        return Jwts.builder()
                .signWith(signingKey, JwtConstants.ALGORITHM)
                .header()
                .add("typ", JwtConstants.TOKEN_TYPE)
                .and()
                .claim("sub", id)
                .claim("iat", new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProps.getRefreshTokenExp()))
                .compact();
    }

    public UsernamePasswordAuthenticationToken setAuthentication(String jwt) throws Exception {
        SecretKey signingKey = jwtProps.getSigningKey();

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(jwt);

            Long uid = claims.getPayload().get("uid", Long.class);
            if (uid == null) {
                throw new Exception("유효하지 않은 토큰: uid 누락");
            }

            return new UsernamePasswordAuthenticationToken(uid, null);
        } catch (ExpiredJwtException e) {
            // 만료되면 refresh token 확인 요청
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "만료된 AccessToken");
        } catch (Exception e) {
            // 서명 불일치, 잘못된 토큰
            throw new Exception("유효하지 않은 토큰");
        }
    }
}
