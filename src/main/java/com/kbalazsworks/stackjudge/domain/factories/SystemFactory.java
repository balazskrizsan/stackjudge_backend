package com.kbalazsworks.stackjudge.domain.factories;

import org.springframework.stereotype.Component;

@Component
public class SystemFactory
{
    public long getCurrentTimeMillis()
    {
        return System.currentTimeMillis();
    }
}
