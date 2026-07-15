package com.jindero.banking.shared.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    @Value("${jwt.secret}")
    private String secretKey;

    @Value(("${jwt.expiration}"))
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Vygenerování tokenu
    public  String generateToken (String email){
       return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Získání emailu z tokenu
    public String extractEmail(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validace tokenu
    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage());
        }
        return false;
    }

}
