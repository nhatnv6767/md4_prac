package com.ra.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret; // Khoa bi mat dung de ky va xac thuc token

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // Lay username tu token
    // Input: token can trich xuat
    // Output: username duoc ma hoa trong token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Ham chung de trich xuat bat ky thong tin nao tu token
    // Input:
    // - token: chuoi JWT token
    // - claimsResolver: function de lay thong tin cu the tu Claims
    // Output: thong tin duoc trich xuat theo kieu T
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Tao token voi thong tin mac dinh
    // Input: thong tin nguoi dung
    // Output: JWT token
    // Ham nay tao JWT token chi voi thong tin co ban cua user
    // Su dung HashMap rong vi:
    // 1. Khong can them bat ky thong tin phu nao (extraClaims) vao token
    // 2. HashMap rong la cach don gian de truyen tham so extraClaims vao ham
    // generateToken()
    // 3. Token chi can chua thong tin co ban nhu username, thoi gian tao, thoi gian
    // het han
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Tao token voi thong tin bo sung
    // Input:
    // - extraClaims: thong tin bo sung can ma hoa vao token
    // - userDetails: thong tin nguoi dung
    // Output: JWT token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Tao refresh token
    // Input: thong tin nguoi dung
    // Output: refresh token voi thoi gian song lau hon
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    // Ham chung de xay dung token
    // Input:
    // - extraClaims: thong tin bo sung
    // - userDetails: thong tin nguoi dung
    // - expiration: thoi gian het han
    // Output: JWT token hoan chinh
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims) // Them thong tin bo sung
                .setSubject(userDetails.getUsername()) // Set username
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thoi diem tao
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Thoi diem het han
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Ky token voi thuat toan HS256
                .compact(); // Tao chuoi token
    }

    // Kiem tra token co hop le khong
    // Input:
    // - token: chuoi token can kiem tra
    // - userDetails: thong tin nguoi dung
    // Output: true neu token hop le, false neu khong
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Kiem tra token da het han chua
    // Input: token can kiem tra
    // Output: true neu token het han, false neu chua
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Lay thoi gian het han tu token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Trich xuat tat ca thong tin tu token
    // Input: token can trich xuat
    // Output: doi tuong Claims chua thong tin
    // Xu ly cac loi:
    // - ExpiredJwtException: token het han
    // - JwtException: token khong hop le
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey()) // Set khoa de xac thuc
                    .build()
                    .parseClaimsJws(token) // Parse token
                    .getBody(); // Lay phan claims

        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        } catch (JwtException e) {
            log.error("JWT token is invalid: {}", e.getMessage());
            throw new JwtException(e.getMessage());
        }
    }

    // Lay khoa bi mat de ky token
    // Output: doi tuong Key dung de ky va xac thuc token
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret); // Giai ma khoa tu Base64
        return Keys.hmacShaKeyFor(keyBytes); // Tao khoa HMAC SHA
    }
}
