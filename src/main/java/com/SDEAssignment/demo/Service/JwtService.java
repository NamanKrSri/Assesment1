package com.SDEAssignment.demo.Service;

import com.SDEAssignment.demo.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
public String generateToken(String email) {
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 1 hour
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
}

    // Extract username
    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    // Validate token
    public boolean validateToken(String token, String email) {
        return (email.equals(extractEmail(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date exp = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getExpiration();
        return exp.before(new Date());
    }
}
