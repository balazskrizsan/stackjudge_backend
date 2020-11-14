package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record Address(
    Long id,
    Long companyId,
    String fullAddress,
    Double markerLat,
    Double markerLng,
    Double manualMarkerLat,
    Double manualMarkerLng,
    LocalDateTime createdAt,
    Long createdBy
)
{
}
