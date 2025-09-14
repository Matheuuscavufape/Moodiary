package com.diario.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {
  @Value("${app.jwt.secret}")
  private String secret;

  @Value("${app.jwt.expiration}")
  private long expirationMs;

  public String generate(UserDetails user) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + expirationMs);
    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim("roles", user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean isValid(String token, UserDetails user) {
    try {
      final String username = extractUsername(token);
      Date exp = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build()
          .parseClaimsJws(token).getBody().getExpiration();
      return username.equals(user.getUsername()) && exp.after(new Date());
    } catch (JwtException e) {
      return false;
    }
  }
}
