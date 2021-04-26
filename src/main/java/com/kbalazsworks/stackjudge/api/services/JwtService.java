package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.String.format;

@Slf4j
@Service
public class JwtService
{
    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP"; //@todo: remove and change
    private final String jwtIssuer = "example.io"; //@todo: remove and change

    public String generateAccessToken(@NonNull User user)
    {
        return Jwts.builder()
                   .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                   .setIssuer(jwtIssuer)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    public String getUserId(String token)
    {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token)
    {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token)
    {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token)
    {
        try
        {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        } catch (SignatureException ex)
        {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex)
        {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex)
        {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex)
        {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex)
        {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }

        return false;
    }
}
