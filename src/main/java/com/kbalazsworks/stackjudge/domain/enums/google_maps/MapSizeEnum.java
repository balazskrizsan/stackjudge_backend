package com.kbalazsworks.stackjudge.domain.enums.google_maps;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MapSizeEnum
{
    COMPANY_HEADER(1300, 450, (short) 1, (short) 16, (short) 1),
    COMPANY_LEFT(660, 220, (short) 1, (short) 16, (short) 1);

    private final int   x;
    private final int   y;
    private final short scale;
    private final short zoom;
    private final short mapType;
}
