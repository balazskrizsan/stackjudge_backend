package com.kbalazsworks.stackjudge.domain.notification_module.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum NotificationTypeEnum
{
    PROTECTED_VIEW((short) 1);

    final private        short                            value;
    private static final Map<Short, NotificationTypeEnum> ENUM_MAP;

    NotificationTypeEnum(short value)
    {
        this.value = value;
    }

    public short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, NotificationTypeEnum> map = new ConcurrentHashMap<>();
        for (NotificationTypeEnum instance : NotificationTypeEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static NotificationTypeEnum getByValue(short name)
    {
        return ENUM_MAP.get(name);
    }
}
