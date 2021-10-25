package com.kbalazsworks.stackjudge.domain.maps_module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MapSizeEnum
{
    COMPANY_HEADER(1300, 450, (short) 1, (short) 16, MapTypeEnum.SATELLITE),
    COMPANY_LEFT(660, 220, (short) 1, (short) 16, MapTypeEnum.ROADMAP);

    private final int         x;
    private final int         y;
    private final short       scale;
    private final short       zoom;
    private final MapTypeEnum mapType;
}
