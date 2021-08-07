package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record GoogleStaticMapsCache(String hash, String fileName, LocalDateTime updatedAt)
{
}
