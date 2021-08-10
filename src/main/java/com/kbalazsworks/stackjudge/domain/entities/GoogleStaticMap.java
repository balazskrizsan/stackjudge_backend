package com.kbalazsworks.stackjudge.domain.entities;

import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapTypeEnum;

public record GoogleStaticMap(
    int sizeX,
    int sizeY,
    short scale,
    short zoom,
    MapTypeEnum mapType,
    double centerLat,
    double centerLng
)
{
}
