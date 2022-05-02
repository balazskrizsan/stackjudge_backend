package com.kbalazsworks.stackjudge.api.services.jwt_service;

import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtSubService
{
    private final ApplicationProperties applicationProperties;
    private final JwtFactory            jwtFactory;

    public Jws<Claims> errorHandledParseClaimsJws(@NonNull String token)
    {
        try
        {
            return jwtFactory
                .createJwtParser()
                .setSigningKey(applicationProperties.getJwtSecret())
                .parseClaimsJws(token);
        }
        catch (SignatureException e)
        {
            log.error("Invalid JWT signature: {}", e.getMessage(), e);
        }
        catch (MalformedJwtException e)
        {
            log.error("Invalid JWT token: {}", e.getMessage(), e);
        }
        catch (ExpiredJwtException e)
        {
            log.error("Expired JWT token: {}", e.getMessage(), e);
        }
        catch (UnsupportedJwtException e)
        {
            log.error("Unsupported JWT error: {}", e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.error("Unknown JWT error: {}", e.getMessage(), e);
        }

        throw new JwtException("Invalid authentication error");
    }

    public String getUserDataFormJwtString(String token, int dataIndex)
    {
        try
        {
            return errorHandledParseClaimsJws(token).getBody().getSubject().split(",")[dataIndex];
        }
        catch (JwtException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error("Jwt get user data error on id#{}; {}", dataIndex, e.getMessage(), e);

            throw new JwtException("Invalid authentication error");
        }
    }
}
