package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("CompanyOwners")
public record CompanyOwners(
    @JsonProperty Long id,
    @JsonProperty List<Long> owners
) implements IRedisCacheable
{
}
