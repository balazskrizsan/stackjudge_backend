package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("CompanyAddresses")
public record CompanyAddresses(
    @Id
    @JsonProperty
    Long companyId,

    @JsonProperty
    List<Address> addresses
) implements IRedisCacheable
{
    @Override
    public Long redisCacheId()
    {
        return companyId;
    }
}
