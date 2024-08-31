package com.wealth_health.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public long getExpirationTime() {
        return jwtExpiration;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder() // Starts the process of building a JWT
                .setClaims(extraClaims) // Set the claims in the token
                .setSubject(userDetails.getUsername()) // Sets the subject of the token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets the issued at date
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Sets the expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Signs token with specified algo and key
                .compact(); // Convert configured JWT into compact, URL-safe string
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder() // Starts the process of creating a JwtParser instance
                .setSigningKey(getSignInKey()) // Sets the signing key used to signed the JWT
                .build() // Build JwtParser instance with the configured settings
                .parseClaimsJws(token) // Parse provided token and return the payload, header and signature
                .getBody(); // extract the claims encoded in the jws
    }

}
