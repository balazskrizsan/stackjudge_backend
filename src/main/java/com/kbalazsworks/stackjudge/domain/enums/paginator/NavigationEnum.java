package com.kbalazsworks.stackjudge.domain.enums.paginator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum NavigationEnum
{
    @JsonProperty("1")
    FIRST((short) 1),
    @JsonProperty("2")
    SECOND((short) 2),
    @JsonProperty("3")
    CURRENT_MINUS_2((short) 3),
    @JsonProperty("4")
    CURRENT_MINUS_1((short) 4),
    @JsonProperty("5")
    CURRENT((short) 5),
    @JsonProperty("6")
    CURRENT_PLUS_1((short) 6),
    @JsonProperty("7")
    CURRENT_PLUS_2((short) 7),
    @JsonProperty("8")
    LAST_MINUS_1((short) 8),
    @JsonProperty("9")
    LAST((short) 9);

    final private        Short                      value;
    private static final Map<Short, NavigationEnum> ENUM_MAP;

    NavigationEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, NavigationEnum> map = new ConcurrentHashMap<>();
        for (NavigationEnum instance : NavigationEnum
            .values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static NavigationEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
