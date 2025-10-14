package com.gowtham.project01.utils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.gowtham.project01.Schema.TokenModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiresIn = (24 * 60) * 60 * 1000;
    private final long refreshTokenExpire = (7 * 24 * 60) * 60 * 1000;

    public long GetExpirationTime() {
        return expiresIn;
    }

    public String GetAccessToken(String username, UUID id) {
        String value = username + "//" + id.toString();
        return Jwts.builder().setId(id.toString()).setSubject(value).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn)).signWith(key).compact();
    }

    public String GetRefreshToken(String username, UUID id) {
        String value = username + "//" + id.toString();
        return Jwts.builder().setId(id.toString()).setSubject(value).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpire)).signWith(key).compact();
    }

    public String GetUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
    }

    public boolean IsTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TokenModel GetUsernameFromToken(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        String id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
        return new TokenModel(subject, id);
    }

}
