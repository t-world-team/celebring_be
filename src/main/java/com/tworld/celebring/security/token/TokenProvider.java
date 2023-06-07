package com.tworld.celebring.security.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class TokenProvider implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private Key key;
    private final String secretKey = "celebring-secret-token-key";

    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 3 * 1000; // 3시간

    @Override
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(secretKey.getBytes()).getBytes());
    }

    public String generateToken(Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        Date now = new Date();
        Date expire = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        Claims claims = Jwts.claims();
        claims.put("auth", user.getAuthorities());
        claims.put("user", user.getAttributes());
        claims.put("expire", expire);

        return Jwts.builder()
                .setSubject(user.getAuthorities().toString())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Map<String, Object> user = (Map<String, Object>) claims.get("user");

        User principal = new User(user.get("id").toString(), null, AuthorityUtils.createAuthorityList());

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported Jwt Token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
