package com.kbalazsworks.stackjudge.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Address(
    Long id,
    Long companyId,
    String fullAddress,
    BigDecimal markerLat,
    BigDecimal markerLng,
    BigDecimal manualMarkerLat,
    BigDecimal manualMarkerLng,
    LocalDateTime createdAt,
    Long createdBy
)
{
}
