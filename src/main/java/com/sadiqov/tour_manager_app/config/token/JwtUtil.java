package com.sadiqov.tour_manager_app.config.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    String secretKey;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return (validateToken(token) && getUsername(token).equals(userDetails.getUsername()));
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

}