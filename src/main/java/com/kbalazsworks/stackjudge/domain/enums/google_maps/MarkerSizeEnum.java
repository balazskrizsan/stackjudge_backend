package com.kbalazsworks.stackjudge.domain.enums.google_maps;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MarkerSizeEnum
{
    // @todo: check size order
    TINY((short) 1),
    MID((short) 2),
    SMALL((short) 3);

    private static final Map<Short, MarkerSizeEnum> ENUM_MAP;

    final private short value;

    MarkerSizeEnum(short value)
    {
        this.value = value;
    }

    public short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, MarkerSizeEnum> map = new ConcurrentHashMap<>();
        for (MarkerSizeEnum instance : MarkerSizeEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static MarkerSizeEnum getByValue(short name)
    {
        return ENUM_MAP.get(name);
    }
}
