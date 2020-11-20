package com.kbalazsworks.stackjudge.api.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum SearchLimitEnum
{
    SHORT((short) 8),
    DEFAULT((short) 16),
    LONG((short) 32);

    final private        Short                       value;
    private static final Map<Short, SearchLimitEnum> ENUM_MAP;

    SearchLimitEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, SearchLimitEnum> map = new ConcurrentHashMap<>();
        for (SearchLimitEnum instance : SearchLimitEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static SearchLimitEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
