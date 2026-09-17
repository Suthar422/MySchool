package com.bookseat.authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    // Use a strong secret (at least 32 chars)
    private final String SECRET = "mySuperSecretKeyForJwtGeneration@Jwt!1234567890";
    private final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private final long EXPIRATION_TIME = 1000 * 60 * 60L; // 1 hour

    public String generateToken(UserUtil principal) {
        return Jwts.builder()
                .subject(principal.getUsername())
                .issuer(principal.getSchoolCode())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractSchoolCode(String token) {
        String schoolCode = extractClaims(token).getIssuer();
        log.info("extractSchoolCode: {}", schoolCode);
        return schoolCode;
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, String username, String schoolCode) {
        return extractUsername(token).equals(username) &&
                extractSchoolCode(token).equals(schoolCode) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
