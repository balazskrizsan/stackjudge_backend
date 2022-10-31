package com.kbalazsworks.stackjudge.domain.address_module.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.common_module.entities.IRedisCacheable;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("Address")
public record Address(
    @JsonProperty Long id,
    @JsonProperty Long companyId,
    @JsonProperty String fullAddress,
    @JsonProperty Double markerLat,
    @JsonProperty Double markerLng,
    @JsonProperty Double manualMarkerLat,
    @JsonProperty Double manualMarkerLng,
    @JsonProperty LocalDateTime createdAt,
    @JsonProperty String createdBy
) implements IRedisCacheable
{
    @Override
    public Long redisCacheId()
    {
        return companyId;
    }
}
