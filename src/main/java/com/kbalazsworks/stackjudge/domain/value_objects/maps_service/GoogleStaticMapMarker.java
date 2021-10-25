package com.kbalazsworks.stackjudge.domain.value_objects.maps_service;

import com.kbalazsworks.stackjudge.domain.maps_module.enums.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.maps_module.enums.MarkerSizeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // @todo3: do I need it?
@AllArgsConstructor
@Getter
public class GoogleStaticMapMarker
{
    private MarkerSizeEnum  size;
    private MarkerColorEnum color;
    private String          label;
    private double          centerLat;
    private double          centerLng;
}
