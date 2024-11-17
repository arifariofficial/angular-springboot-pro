package ariful.backend_springboot.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMillis}")
    private long expirationMillis;

    private Key key;

    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            key = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            // If the secret is not Base64 encoded, use it directly
            key = Keys.hmacShaKeyFor(secret.getBytes());
        }
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token)
               .getBody();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
