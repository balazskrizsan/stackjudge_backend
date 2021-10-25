package com.kbalazsworks.stackjudge.domain.maps_module.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MapPositionEnum
{
    DEFAULT((short) 0),
    COMPANY_HEADER((short) 1),
    COMPANY_LEFT((short) 2);

    private final static Map<Short, MapPositionEnum> ENUM_MAP;
    private final short  value;

    MapPositionEnum(short value)
    {
        this.value = value;
    }

    @JsonValue
    public short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, MapPositionEnum> map = new ConcurrentHashMap<>();
        for (MapPositionEnum instance : MapPositionEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static MapPositionEnum getByValue(short name)
    {
        return ENUM_MAP.get(name);
    }
}

