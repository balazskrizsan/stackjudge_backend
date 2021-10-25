package com.kbalazsworks.stackjudge.domain.review_module.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ItemTypeEnum
{
    @JsonProperty("1")
    PAGE((short) 1),
    @JsonProperty("2")
    SPACER((short) 2);

    final private        Short                    value;
    private static final Map<Short, ItemTypeEnum> ENUM_MAP;

    ItemTypeEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, ItemTypeEnum> map = new ConcurrentHashMap<>();
        for (ItemTypeEnum instance : ItemTypeEnum
            .values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static ItemTypeEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
