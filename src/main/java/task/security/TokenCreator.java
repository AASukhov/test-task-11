package task.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenCreator {
    @Value("${jwt.token.secretKey}")
    String secretWord;

    @Value("${jwt.token.validTime}")
    int expirationTime;

    public String createToken (Authentication authentication) {
        String username = authentication.getName();
        Date current = new Date();
        Date expiration = new Date(current.getTime() + expirationTime);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
        return token;
    }

    public String getNameFromToken (String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Token is expired or incorrect");
        }
    }
}
