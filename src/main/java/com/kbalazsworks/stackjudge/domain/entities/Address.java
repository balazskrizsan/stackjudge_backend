package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Address(
    @JsonProperty Long id,
    @JsonProperty Long companyId,
    @JsonProperty String fullAddress,
    @JsonProperty Double markerLat,
    @JsonProperty Double markerLng,
    @JsonProperty Double manualMarkerLat,
    @JsonProperty Double manualMarkerLng,
    @JsonProperty LocalDateTime createdAt,
    @JsonProperty Long createdBy
)
{
}
