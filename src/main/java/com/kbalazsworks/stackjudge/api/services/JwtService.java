package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
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
    private final DateFactory           dateFactory;
    private final SystemFactory         systemFactory;

    private final int USER_ID_INDEX = 0;

    private final long ONE_WEEK = 7 * 24 * 60 * 60 * 1000;

    public String generateAccessToken(@NonNull User user)
    {
        return Jwts
            .builder()
            .setSubject(format("%s,%s,%s", user.getId(), user.getUsername(), user.getProfilePictureUrl()))
            .setIssuer(applicationProperties.getSiteDomain())
            .setIssuedAt(dateFactory.create())
            .setExpiration(dateFactory.create(systemFactory.getCurrentTimeMillis() + ONE_WEEK))
            .signWith(SignatureAlgorithm.HS512, applicationProperties.getJwtSecret())
            .compact();
    }

    public Long getUserId(String token)
    {
        Claims claims = Jwts
            .parser()
            .setSigningKey(applicationProperties.getJwtSecret())
            .parseClaimsJws(token)
            .getBody();

        return Long.valueOf(claims.getSubject().split(",")[USER_ID_INDEX]);
    }

    public String getUsername(@NonNull String token)
    {
        Claims claims = Jwts
            .parser()
            .setSigningKey(applicationProperties.getJwtSecret())
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(@NonNull String token) throws ExpiredJwtException
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
