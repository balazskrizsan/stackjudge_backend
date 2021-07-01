package com.kbalazsworks.stackjudge.domain.enums.review_table;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum VisibilityEnum
{
    EVERYBODY((short) 1),
    REGISTERED((short) 2),
    PROTECTED((short) 3);

    final private        Short                value;
    private static final Map<Short, VisibilityEnum> ENUM_MAP;

    VisibilityEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, VisibilityEnum> map = new ConcurrentHashMap<>();
        for (VisibilityEnum instance : VisibilityEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static VisibilityEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
