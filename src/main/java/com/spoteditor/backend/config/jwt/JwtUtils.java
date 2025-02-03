package com.spoteditor.backend.config.jwt;

import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.spoteditor.backend.global.response.ErrorCode.INVALID_TOKEN;
import static com.spoteditor.backend.global.response.ErrorCode.TOKEN_EXPIRED;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String createToken(Long id, long expirationTime) {
        SecretKey signingKey = jwtProperties.getSigningKey();

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
        return createToken(id, jwtProperties.getAccessTokenExp());
    }

    public String createRefreshToken(Long id) {
        return createToken(id, jwtProperties.getRefreshTokenExp());
    }

    public UsernamePasswordAuthenticationToken setAuthentication(String jwt) throws Exception {
        SecretKey signingKey = jwtProperties.getSigningKey();

        String id = parseJwtGetUid(jwt, signingKey);

        UserIdDto userIdDto = new UserIdDto(Long.parseLong(id));

        return new UsernamePasswordAuthenticationToken(userIdDto, null);
    }

    private String parseJwtGetUid(String jwt, SecretKey signingKey) {
        try {
            Jws<Claims> claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(jwt);

            return (String) claims.getPayload().get("sub");
        } catch (ExpiredJwtException e) {
            throw new UserException(TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new UserException(INVALID_TOKEN);
        }
    }
}
