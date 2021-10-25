package com.kbalazsworks.stackjudge.domain.group_module.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TypeEnum
{
    PROJECT((short) 1),
    TEAM((short) 2),
    STACK((short) 3),
    TECHNOLOGY((short) 4);

    final private        Short                value;
    private static final Map<Short, TypeEnum> ENUM_MAP;

    TypeEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, TypeEnum> map = new ConcurrentHashMap<>();
        for (TypeEnum instance : TypeEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static TypeEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}

