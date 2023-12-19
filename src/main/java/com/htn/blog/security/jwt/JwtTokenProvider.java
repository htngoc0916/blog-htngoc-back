package com.htn.blog.security.jwt;

import com.htn.blog.exception.BlogApiException;
import com.htn.blog.security.custom.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${blog.jwt-secret}")
    private String jwtSecret;
    @Value("${blog.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateJwtToken(CustomUserDetailsServiceImpl userDetailsImpl){
        return generateTokenFromUsername(userDetailsImpl.getEmail());
    }

    public String generateTokenFromUsername(String username) {
        Date curentDate = new Date();
        Date expireDate = new Date(curentDate.getTime() + jwtExpirationDate);
        //tao ra token
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(curentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    // token key
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //get username from jwt token
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }

    //validate Jwt token
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT token: {}", ex.getMessage());
            throw new BlogApiException("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT token: {}", ex.getMessage());
            throw new BlogApiException("Expired JWT token");
        }catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
            throw new BlogApiException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
            throw new BlogApiException("JWT claims string is empty");
        }
    }
}
