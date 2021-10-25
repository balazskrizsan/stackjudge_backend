package com.kbalazsworks.stackjudge.api.value_objects.maps;

import com.kbalazsworks.stackjudge.domain.maps_module.enums.MapPositionEnum;

public interface IStaticProxy
{
    String location();

    MapPositionEnum mapPositionEnum();
}
