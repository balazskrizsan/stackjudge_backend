package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    private final JwtSubService         jwtSubService;

    private final int USER_ID_INDEX             = 0;
    private final int USER_NAME_INDEX           = 1;
    private final int PROFILE_PICTURE_URL_INDEX = 2;

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

    // @todo: test
    public boolean isExpired(@NonNull String token)
    {
        return getExpirationDate(token).after(dateFactory.create());
    }

    public Long getUserId(@NonNull String token)
    {
        return Long.valueOf(jwtSubService.getUserDataFormJwtString(token, USER_ID_INDEX));
    }

    public String getUsername(@NonNull String token)
    {
        return jwtSubService.getUserDataFormJwtString(token, USER_NAME_INDEX);
    }

    public String getProfilePictureUrl(@NonNull String token)
    {
        return jwtSubService.getUserDataFormJwtString(token, PROFILE_PICTURE_URL_INDEX);
    }

    // @todo: test
    public Date getExpirationDate(@NonNull String token)
    {
        return jwtSubService.errorHandledParseClaimsJws(token).getBody().getExpiration();
    }

    // @todo: test
    public boolean isIntegrityValid(@NonNull String token)
    {
        try
        {
            jwtSubService.errorHandledParseClaimsJws(token);

            return true;
        }
        catch (Exception ignored)
        {
        }

        return false;
    }

    // @todo: test
    // @todo: add expiry check
    public void validate(@NonNull String token)
    {
        if (!isIntegrityValid(token))
        {
            throw new JwtException("Invalid authentication error");
        }
    }
}
