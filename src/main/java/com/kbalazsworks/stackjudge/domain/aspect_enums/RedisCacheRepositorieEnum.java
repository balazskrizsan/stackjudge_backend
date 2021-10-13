package com.kbalazsworks.stackjudge.domain.aspect_enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum RedisCacheRepositorieEnum
{
    ADDRESS(1);

    final private Integer value;

    private static final Map<Integer, RedisCacheRepositorieEnum> ENUM_MAP;

    RedisCacheRepositorieEnum(Integer value)
    {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }

    static
    {
        Map<Integer, RedisCacheRepositorieEnum> map = new ConcurrentHashMap<>();
        for (RedisCacheRepositorieEnum instance : RedisCacheRepositorieEnum
            .values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static RedisCacheRepositorieEnum getByValue(Integer name)
    {
        return ENUM_MAP.get(name);
    }
}