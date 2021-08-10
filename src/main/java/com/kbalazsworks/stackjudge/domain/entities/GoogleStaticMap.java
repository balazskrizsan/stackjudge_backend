package com.kbalazsworks.stackjudge.domain.entities;

import lombok.NonNull;

import java.time.LocalDateTime;

public record GoogleStaticMap(
    int sizeX,
    int sizeY,
    short scale,
    short zoom,
    short mapType,
    double centerLat,
    double centerLng
)
{
}
