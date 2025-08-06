package com.testing_company.case_management.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    @Value("${JWT_SECRET}")
    private String secret;
    @Value("${JWT_EXPIRATION:900}")
    private Long jwtExpiration;
    @Value("${JWT_REFRESH_EXPIRATION:604800}")
    private Long refreshTokenDuration;

    public String generateToken(UserDetails userDatails){
        Map<String, Object>claims=new HashMap<>();

        Collection<? extends GrantedAuthority>authorities=userDatails.getAuthorities();
        claims.put("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return createToken(claims, userDatails.getUsername());
    }
    public String createToken(Map<String, Object>claims, String username){
        Date now=new Date();
        Date expiryDate=new Date(now.getTime()+jwtExpiration*1000);
        SecretKey key=getSigningKey();
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserDetails userDetails){
        return generateTokenWithExpiration(userDetails, jwtExpiration);
    }
    private String generateTokenWithExpiration(UserDetails userDetails, Long expiration){
        Map<String, Object>claims=new HashMap<>();
        Collection<?extends GrantedAuthority>authorities=userDetails.getAuthorities();
        claims.put("roles",authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        Date now=new Date();
        Date expiryDate=new Date(now.getTime()+expiration*1000);
        SecretKey key=getSigningKey();

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
    public String generateTokenWithClaims(String username, Map<String, Object>extractClaims){
        Map<String, Object>claims=new HashMap<>(extractClaims);
        return createToken(claims, username);
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        try{
            final String username=extractUsername(token);
            return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
        }catch (JwtException | IllegalArgumentException e){
            log.error("JWT validation failed：{}",e.getMessage());
            return false;
        }
    }
    public Boolean isTokenExpired(String token){
        try{
            final Date expiration=extractExpiration(token);
            return expiration.before(new Date());
        }catch (Exception e){
            log.error("Failed to extract expiration from token：{}", e.getMessage());
            return true;
        }
    }
    public <T> T extractClaim(String token, Function<Claims, T>claimsResolver){
        final Claims claims=extractAllClaim(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaim(String token){
        SecretKey key=getSigningKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenExpiringSoon(String token){
        try{
            Date expiration=extractExpiration(token);
            Date now=new Date();
            long timeUntilExpiry=expiration.getTime()-now.getTime();
            return timeUntilExpiry<30*60*1000;
        }catch (JwtException e){
            return true;
        }
    }
}
