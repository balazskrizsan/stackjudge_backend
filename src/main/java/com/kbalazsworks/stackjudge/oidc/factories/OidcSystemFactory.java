package com.kbalazsworks.stackjudge.oidc.factories;

import org.springframework.stereotype.Component;

@Component
public class OidcSystemFactory
{
    public long getCurrentTimeMillis()
    {
        return System.currentTimeMillis();
    }
}
