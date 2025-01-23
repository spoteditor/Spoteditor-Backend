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

    public String createToken(Long id, long expirationTime) {
        SecretKey signingKey = jwtProps.getSigningKey();

        return Jwts.builder()
                .signWith(signingKey, JwtConstants.ALGORITHM)
                .header()
                .add("typ", JwtConstants.TOKEN_TYPE)
                .and()
                .claim("sub", id.toString())
                .claim("iat", new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public String createAccessToken(Long id) {
        return createToken(id, jwtProps.getAccessTokenExp());
    }

    public String createRefreshToken(Long id) {
        return createToken(id, jwtProps.getRefreshTokenExp());
    }

    public UsernamePasswordAuthenticationToken setAuthentication(String jwt) throws Exception {
        SecretKey signingKey = jwtProps.getSigningKey();

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(jwt);
            String uid = (String) claims.getPayload().get("sub");

            return new UsernamePasswordAuthenticationToken(uid, null);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), null);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
