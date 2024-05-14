package com.kyljmeeski.onlinequeue.security;

import com.kyljmeeski.onlinequeue.entity.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private final JwtParser jwtParser;
//    @Value("${jwt.secret}")
    private String SECRET = "my_secret_key";
//    @Value("${jwt.validity_in_seconds}")
    private long VALIDITY_IN_SECONDS = 60 * 60;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(SECRET);
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("name", user.getName());
        Date issuedAt = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(
                        Date.from(
                                issuedAt.toInstant().plusSeconds(VALIDITY_IN_SECONDS)
                        )
                )
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    private Claims parseClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if (token != null) {
                return parseClaims(token);
            }
            return null;
        } catch (ExpiredJwtException exception) {
            request.setAttribute("expired", exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            request.setAttribute("invalid", exception.getMessage());
            throw exception;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception exception) {
            throw exception;
        }
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    public List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

    public long getUserId(String token) {
        Claims claims = parseClaims(token);
        return (long) claims.get("user_id");
    }
}
