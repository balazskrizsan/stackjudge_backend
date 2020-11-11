package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Company(
    @JsonProperty Long id,
    @JsonProperty String name,
    @JsonProperty short companySizeId,
    @JsonProperty short itSizeId,
    LocalDateTime createdAt,
    Long createdBy
)
{
}
