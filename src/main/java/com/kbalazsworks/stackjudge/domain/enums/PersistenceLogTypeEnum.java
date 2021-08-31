package com.kbalazsworks.stackjudge.domain.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum PersistenceLogTypeEnum
{
    OWN_REQUEST_SENT((short) 1);

    private final Short value;

    private static final Map<Short, PersistenceLogTypeEnum> ENUM_MAP;

    PersistenceLogTypeEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, PersistenceLogTypeEnum>
            map
            = new ConcurrentHashMap<>();
        for (PersistenceLogTypeEnum instance : PersistenceLogTypeEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static PersistenceLogTypeEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}