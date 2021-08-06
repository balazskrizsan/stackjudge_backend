package com.kbalazsworks.stackjudge.domain.enums.google_maps;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MarkerColorEnum
{
    BLACK((short) 1),
    BROWN((short) 2),
    GREEN((short) 3),
    PURPLE((short) 4),
    YELLOW((short) 5),
    BLUE((short) 6),
    GRAY((short) 7),
    ORANGE((short) 8),
    RED((short) 9),
    WHITE((short) 10);

    final private        short                       value;
    private static final Map<Short, MarkerColorEnum> ENUM_MAP;

    MarkerColorEnum(short value)
    {
        this.value = value;
    }

    public short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, MarkerColorEnum> map = new ConcurrentHashMap<>();
        for (MarkerColorEnum instance : MarkerColorEnum
            .values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static MarkerColorEnum getByValue(short name)
    {
        return ENUM_MAP.get(name);
    }
}
