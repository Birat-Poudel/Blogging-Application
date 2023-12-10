package com.biratpoudel.blog.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private static final SecretKey key = Keys.hmacShaKeyFor(AppConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication authentication) {
        return Jwts.builder()
                   .setIssuer("com.biratpoudel.blog")
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + 86400 * 1000))
                   .claim("username", authentication.getName())
                   .signWith(key)
                   .compact();
    }

    public static String getUsernameFromJwtToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jwt)
                            .getBody();
        return String.valueOf(claims.get("username"));
    }
}
