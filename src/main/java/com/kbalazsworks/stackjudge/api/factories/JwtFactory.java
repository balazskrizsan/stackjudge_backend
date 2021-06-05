package com.kbalazsworks.stackjudge.api.factories;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory
{
    public JwtParser createJwtParser()
    {
        return Jwts.parser();
    }
}
