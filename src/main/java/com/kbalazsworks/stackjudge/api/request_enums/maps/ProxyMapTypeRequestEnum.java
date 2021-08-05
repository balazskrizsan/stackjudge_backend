package com.kbalazsworks.stackjudge.api.request_enums.maps;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ProxyMapTypeRequestEnum
{
    ROADMAP((short) 1),
    SATELLITE((short) 2),
    HYBRID((short) 3),
    TERRAIN((short) 4);

    final private        Short                               value;
    private static final Map<Short, ProxyMapTypeRequestEnum> ENUM_MAP;

    ProxyMapTypeRequestEnum(Short value)
    {
        this.value = value;
    }

    public Short getValue()
    {
        return this.value;
    }

    static
    {
        Map<Short, ProxyMapTypeRequestEnum> map = new ConcurrentHashMap<>();
        for (ProxyMapTypeRequestEnum instance : ProxyMapTypeRequestEnum.values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static ProxyMapTypeRequestEnum getByValue(Short name)
    {
        return ENUM_MAP.get(name);
    }
}
