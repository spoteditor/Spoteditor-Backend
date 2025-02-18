package com.spoteditor.backend.config.jwt;

import com.spoteditor.backend.global.exception.TokenException;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

import static com.spoteditor.backend.global.response.ErrorCode.INVALID_ACCESS_TOKEN;
import static com.spoteditor.backend.global.response.ErrorCode.TOKEN_EXPIRED;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String createToken(Long id, String role, long expirationTime) {
        SecretKey signingKey = jwtProperties.getSigningKey();

        return Jwts.builder()
                .signWith(signingKey, JwtConstants.ALGORITHM)
                .header()
                .add("typ", JwtConstants.TOKEN_TYPE)
                .and()
                .claim("sub", id.toString())
                .claim("role", role)
                .claim("iat", new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public String createAccessToken(Long id, String role) {
        return createToken(id, role, jwtProperties.getAccessTokenExp());
    }

    public String createRefreshToken(Long id, String role) {
        return createToken(id, role, jwtProperties.getRefreshTokenExp());
    }

    public UsernamePasswordAuthenticationToken setAuthentication(String jwt) throws ExpiredJwtException, IllegalArgumentException, MalformedJwtException, SignatureException {
        SecretKey signingKey = jwtProperties.getSigningKey();

        Long id = Long.parseLong(parseJwtSubject(jwt, "sub", signingKey));
        String role = parseJwtSubject(jwt, "role", signingKey);

        UserIdDto userIdDto = new UserIdDto(id);
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        return new UsernamePasswordAuthenticationToken(userIdDto, null, Collections.singleton(authority));
    }

    private String parseJwtSubject(String jwt, String subject, SecretKey signingKey) {
        try {
            Jws<Claims> claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(jwt);

            return (String) claims.getPayload().get(subject);
        } catch (ExpiredJwtException e) {
            throw new TokenException(TOKEN_EXPIRED);
        } catch (IllegalArgumentException | MalformedJwtException | SignatureException e) {
            throw new TokenException(INVALID_ACCESS_TOKEN);
        }
    }
}
