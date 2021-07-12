package com.kbalazsworks.stackjudge.domain.enums.notification;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum NotificationType
{
    PROTECTED_VIEW((short) 1);

    final private        short                        value;
    private static final Map<Short, NotificationType> ENUM_MAP;

    NotificationType(short value)
    {
        this.value = value;
    }

    public short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, NotificationType> map = new ConcurrentHashMap<>();
        for (NotificationType instance : NotificationType.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static NotificationType getByValue(short name)
    {
        return ENUM_MAP.get(name);
    }
}
