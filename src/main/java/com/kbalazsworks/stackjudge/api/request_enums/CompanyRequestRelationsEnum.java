package com.kbalazsworks.stackjudge.api.request_enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CompanyRequestRelationsEnum
{
    STATISTIC((short) 1),
    GROUP((short) 2),
    REVIEW((short) 3),
    PAGINATOR((short) 4),
    ADDRESS((short) 5),
    OWNER((short) 6);

    final private        Short                                   value;
    private static final Map<Short, CompanyRequestRelationsEnum> ENUM_MAP;

    CompanyRequestRelationsEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, CompanyRequestRelationsEnum> map = new ConcurrentHashMap<>();
        for (CompanyRequestRelationsEnum instance : CompanyRequestRelationsEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CompanyRequestRelationsEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
