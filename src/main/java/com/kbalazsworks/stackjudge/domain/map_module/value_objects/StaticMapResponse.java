package com.kbalazsworks.stackjudge.domain.map_module.value_objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.kbalazsworks.stackjudge.api.value_objects.maps.IStaticProxy;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record StaticMapResponse(
    @JsonProperty String location,
    @JsonProperty MapPositionEnum mapPositionEnum
) implements IStaticProxy
{
}
