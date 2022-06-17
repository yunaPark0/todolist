package example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final String SECRET = "secret";

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Claims claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Boolean isValidToken(String token, UserDetails userDetails) {
        log.info("isValidToken token = {}", token);
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        log.info("getAllClaims token = {}", token);
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        String username = String.valueOf(getAllClaims(token).get("username"));
        log.info("getUsernameFormToken subject = {}", username);
        return username;
    }

    public Date getExpirationDate(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

}
