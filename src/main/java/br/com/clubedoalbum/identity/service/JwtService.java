package br.com.clubedoalbum.identity.service;

import br.com.clubedoalbum.identity.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey secretKey;
  private final long expirationSeconds;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expiration-seconds}") long expirationSeconds
  ) {
    this.secretKey = Keys.hmacShaKeyFor(normalizeSecret(secret).getBytes(StandardCharsets.UTF_8));
    this.expirationSeconds = expirationSeconds;
  }

  public String generateToken(User user) {
    Instant now = Instant.now();
    Instant expiresAt = now.plusSeconds(expirationSeconds);

    return Jwts.builder()
        .subject(user.getId().toString())
        .claim("name", user.getName())
        .claim("email", user.getEmail())
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiresAt))
        .signWith(secretKey)
        .compact();
  }

  public long getExpirationSeconds() {
    return expirationSeconds;
  }

  private String normalizeSecret(String secret) {
    if (secret == null || secret.length() < 32) {
      return "clube-do-album-local-development-secret-key-change-me";
    }

    return secret;
  }
}
