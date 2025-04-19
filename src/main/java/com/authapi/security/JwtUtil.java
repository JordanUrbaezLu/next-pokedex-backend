package com.authapi.security;

import com.authapi.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

/**
 * JwtUtil
 *
 * <p>Utility component for generating and validating JWTs.
 *
 * <p>• Generates HS256-signed tokens with: – Subject set to user’s email. – Custom claims for user
 * ID and name. – Issued-at and 1-day expiration. • Parses incoming tokens to extract the
 * authenticated user’s email. • Backed by an in-memory SecretKey; swap for a persistent key in
 * production.
 */
@Component
public class JwtUtil {

  // 🔐 Secure key (32 bytes minimum for HS256)
  private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .claim("id", user.getId())
        .claim("name", user.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
        .signWith(key)
        .compact();
  }

  public String extractEmail(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
