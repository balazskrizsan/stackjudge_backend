package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapTypeEnum;

public class GoogleStaticMapFakeBuilder
{
    public static final int         sizeX     = 1;
    public static final int         sizeY     = 2;
    public static final short       scale     = 3;
    public static final short       zoom      = 4;
    public static final MapTypeEnum mapType   = MapTypeEnum.ROADMAP;
    public static final double      centerLat = 5;
    public static final double      centerLng = 6;

    public GoogleStaticMap build()
    {
        return new GoogleStaticMap(sizeX, sizeY, scale, zoom, mapType, centerLat, centerLng);
    }
}
