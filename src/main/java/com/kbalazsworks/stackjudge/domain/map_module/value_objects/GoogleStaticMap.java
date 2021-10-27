package com.kbalazsworks.stackjudge.domain.map_module.value_objects;

import com.kbalazsworks.stackjudge.domain.map_module.enums.MapTypeEnum;

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
