package com.kbalazsworks.stackjudge.api.value_objects.maps;

import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;

public interface IStaticProxy
{
    String location();

    MapPositionEnum mapPositionEnum();
}
