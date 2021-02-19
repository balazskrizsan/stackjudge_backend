package com.kbalazsworks.stackjudge.domain.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class DateTimeFormatterService
{
    public long toEpoch(@NonNull LocalDateTime now)
    {
        return now.toEpochSecond(ZoneOffset.UTC);
    }
}
