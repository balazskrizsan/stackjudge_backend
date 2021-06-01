package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService
{
    private final ApplicationProperties applicationProperties;

    public String generateAccessToken(@NonNull User user)
    {
        return Jwts
            .builder()
            .setSubject(format("%s,%s,%s", user.getId(), user.getUsername(), user.getProfilePictureUrl()))
            .setIssuer(applicationProperties.getSiteDomain())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
            .signWith(SignatureAlgorithm.HS512, applicationProperties.getJwtSecret())
            .compact();
    }

    public Long getUserId(String token)
    {
        Claims claims = Jwts.parser()
            .setSigningKey(applicationProperties.getJwtSecret())
            .parseClaimsJws(token)
            .getBody();

        return Long.valueOf(claims.getSubject().split(",")[0]);
    }

    public String getUsername(String token)
    {
        Claims claims = Jwts
            .parser()
            .setSigningKey(applicationProperties.getJwtSecret())
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token)
    {
        Claims claims = Jwts
            .parser()
            .setSigningKey(applicationProperties.getJwtSecret())
            .parseClaimsJws(token)
            .getBody();

        return claims.getExpiration();
    }

    public boolean isValid(@NonNull String token)
    {
        try
        {
            Jwts.parser().setSigningKey(applicationProperties.getJwtSecret()).parseClaimsJws(token);

            return true;
        }
        catch (SignatureException ex)
        {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        }
        catch (MalformedJwtException ex)
        {
            log.error("Invalid JWT token - {}", ex.getMessage());
        }
        catch (ExpiredJwtException ex)
        {
            log.error("Expired JWT token - {}", ex.getMessage());
        }
        catch (UnsupportedJwtException ex)
        {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        }
        catch (IllegalArgumentException ex)
        {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }

        return false;
    }
}
