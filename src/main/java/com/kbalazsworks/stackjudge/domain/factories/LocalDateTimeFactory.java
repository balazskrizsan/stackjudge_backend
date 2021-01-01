package com.kbalazsworks.stackjudge.domain.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LocalDateTimeFactory
{
    private DateFactory dateFactory;

    @Autowired
    public void setDateFactory(DateFactory dateFactory)
    {
        this.dateFactory = dateFactory;
    }

    public LocalDateTime create()
    {
        return dateFactory.create().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
