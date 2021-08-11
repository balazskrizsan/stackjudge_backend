package com.kbalazsworks.stackjudge.domain.value_objects.maps_service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.kbalazsworks.stackjudge.api.value_objects.maps.IStaticProxy;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record StaticMapResponse(
    @JsonProperty String location,
    @JsonProperty MapPositionEnum mapPositionEnum
) implements IStaticProxy
{
}
