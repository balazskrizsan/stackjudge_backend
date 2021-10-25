package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.maps_module.enums.MapTypeEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class GoogleStaticMapFakeBuilder
{
    private int         sizeX     = 1;
    private int         sizeY     = 2;
    private short       scale     = 3;
    private short       zoom      = 4;
    private MapTypeEnum mapType   = MapTypeEnum.ROADMAP;
    private double      centerLat = 5;
    private double      centerLng = 6;

    public GoogleStaticMap build()
    {
        return new GoogleStaticMap(sizeX, sizeY, scale, zoom, mapType, centerLat, centerLng);
    }
}
