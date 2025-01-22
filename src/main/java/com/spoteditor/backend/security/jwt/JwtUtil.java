package com.spoteditor.backend.security.jwt;

import com.spoteditor.backend.common.exceptions.BaseException;
import com.spoteditor.backend.common.exceptions.ErrorCode;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtil {

    private final JwtProps jwtProps;

    public String createAccessToken(Long id) {
        SecretKey signingKey = jwtProps.getSigningKey();

        return Jwts.builder()
                .signWith(signingKey, JwtConstants.ALGORITHM)
                .header()
                .add("typ", JwtConstants.TOKEN_TYPE)
                .and()
                .claim("sub", id)
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

            return new UsernamePasswordAuthenticationToken(uid, null);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), null);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
