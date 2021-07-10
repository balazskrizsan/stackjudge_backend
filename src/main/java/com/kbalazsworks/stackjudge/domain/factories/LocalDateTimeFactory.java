package com.kbalazsworks.stackjudge.domain.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class LocalDateTimeFactory
{
    private final DateFactory dateFactory;

    public LocalDateTime create()
    {
        return dateFactory.create().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
