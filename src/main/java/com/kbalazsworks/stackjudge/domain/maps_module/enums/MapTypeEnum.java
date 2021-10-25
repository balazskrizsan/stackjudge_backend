package com.kbalazsworks.stackjudge.domain.maps_module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum MapTypeEnum
{
    ROADMAP((short)1),
    SATELLITE((short)2),
    HYBRID((short)3),
    TERRAIN((short)4);

    private static final Map<Short, MapTypeEnum> ENUM_MAP;

    private final short value;

    static
    {
        Map<Short, MapTypeEnum> map = new ConcurrentHashMap<>();
        for (MapTypeEnum instance : MapTypeEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static MapTypeEnum getByValue(short name)
    {
        return ENUM_MAP.get(name);
    }
}
