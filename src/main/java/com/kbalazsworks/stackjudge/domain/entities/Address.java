package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("Address")
public record Address (
    @JsonProperty Long id,
    @JsonProperty Long companyId,
    @JsonProperty String fullAddress,
    @JsonProperty Double markerLat,
    @JsonProperty Double markerLng,
    @JsonProperty Double manualMarkerLat,
    @JsonProperty Double manualMarkerLng,
    @JsonProperty LocalDateTime createdAt,
    @JsonProperty Long createdBy
) implements IRedisCacheable
{
}
