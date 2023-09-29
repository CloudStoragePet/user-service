package org.brain.user_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.brain.user_service.config.JwtProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public record JwtUtils(JwtProperties jwtProperties) {
    public static final String ROLES = "roles";

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims();
        claims.put("roles", userDetails.getAuthorities());

        Date issuedDate = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(System.currentTimeMillis() + jwtProperties.lifetime());

        return provideToken(userDetails, claims, issuedDate, expiredDate);
    }

    private String provideToken(UserDetails userDetails, Claims claims, Date issuedDate, Date expiredDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserEmail(String token){
        return getAllClaimsFromToken(token).getSubject();
    }
    public List<SimpleGrantedAuthority> getRoles(String token){
        return extractClaim(token, claims -> claims.get(ROLES, List.class));
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .requireIssuer(jwtProperties().issuer())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Creates Key from BASE64 secret
     * @return Key - SignInKey
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
