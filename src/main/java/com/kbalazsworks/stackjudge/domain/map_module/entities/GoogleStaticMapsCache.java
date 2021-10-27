package com.kbalazsworks.stackjudge.domain.map_module.entities;

import java.time.LocalDateTime;

public record GoogleStaticMapsCache(String hash, String fileName, LocalDateTime updatedAt)
{
}
