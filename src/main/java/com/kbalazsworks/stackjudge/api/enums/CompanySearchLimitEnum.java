package com.kbalazsworks.stackjudge.api.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CompanySearchLimitEnum
{
    SHORT((short) 8),
    DEFAULT((short) 16),
    LONG((short) 32);

    final private        Short                              value;
    private static final Map<Short, CompanySearchLimitEnum> ENUM_MAP;

    CompanySearchLimitEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, CompanySearchLimitEnum> map = new ConcurrentHashMap<>();
        for (CompanySearchLimitEnum instance : CompanySearchLimitEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CompanySearchLimitEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
