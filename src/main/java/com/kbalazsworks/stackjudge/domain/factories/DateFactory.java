package com.kbalazsworks.stackjudge.domain.factories;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateFactory
{
    public Date create()
    {
        return new Date();
    }

    public Date create(long date)
    {
        return new Date(date);
    }
}
