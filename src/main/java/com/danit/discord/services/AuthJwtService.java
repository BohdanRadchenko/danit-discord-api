package com.danit.discord.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthJwtService {
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    @Value("${application.security.jwt.access.key}")
    private String accessKey;
    @Value("${application.security.jwt.access.expiration}")
    private long accessExpiration;
    @Value("${application.security.jwt.refresh.key}")
    private String refreshKey;
    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpiration;

    private Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            String secretKey,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, accessKey, accessExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshKey, refreshExpiration);
    }

    public Claims jwtParser(String key, String token) throws ExpiredJwtException, SignatureException {
        return Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getAccessUsername(String token) throws ExpiredJwtException, SignatureException {
        return jwtParser(accessKey, token).getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}