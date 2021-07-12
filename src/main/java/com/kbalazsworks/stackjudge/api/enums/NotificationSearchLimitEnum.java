package com.kbalazsworks.stackjudge.api.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum NotificationSearchLimitEnum
{
    DEFAULT((short) 10);

    final private        Short                                   value;
    private static final Map<Short, NotificationSearchLimitEnum> ENUM_MAP;

    NotificationSearchLimitEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, NotificationSearchLimitEnum> map = new ConcurrentHashMap<>();
        for (NotificationSearchLimitEnum instance : NotificationSearchLimitEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static NotificationSearchLimitEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
