package com.ecom.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// spring security methods are changed in 0.12 version
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long jwtExpirationInMs;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long jwtExpirationInMs) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(Long userId , String email , String role) {
        long now = System.currentTimeMillis();
         return Jwts.builder()
                 .subject(String.valueOf(userId))
                 .claim("email" , email)
                 .claim("role ", role)
                 .issusedAt(new Date(now))
                 .expiration(new Date(jwtExpirationInMs)).singWith(key)
                 .compact();
    }

    public Jws<Claims> validateTokenk(String token) {
        return Jwt.parser().verifywithKey().build().parseSignedClaims(token);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = validateTokenk(token).getBody();
        return Long.valueOf(claims.getSubject());
    }
}
