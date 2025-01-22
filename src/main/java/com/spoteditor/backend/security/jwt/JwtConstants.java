package com.spoteditor.backend.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

public class JwtConstants {
    public static final MacAlgorithm ALGORITHM = Jwts.SIG.HS512;

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_TYPE = "JWT";
}
