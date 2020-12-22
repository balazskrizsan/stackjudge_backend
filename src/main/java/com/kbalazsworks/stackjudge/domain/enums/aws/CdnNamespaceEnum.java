package com.kbalazsworks.stackjudge.domain.enums.aws;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CdnNamespaceEnum
{
    STATIC("static"),
    COMPANY_LOGOS("company-logos");

    final private        String                        value;
    private static final Map<String, CdnNamespaceEnum> ENUM_MAP;

    CdnNamespaceEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    static
    {
        Map<String, CdnNamespaceEnum> map = new ConcurrentHashMap<>();
        for (CdnNamespaceEnum instance : CdnNamespaceEnum
            .values())
        {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CdnNamespaceEnum getByValue(String name)
    {
        return ENUM_MAP.get(name);
    }
}
