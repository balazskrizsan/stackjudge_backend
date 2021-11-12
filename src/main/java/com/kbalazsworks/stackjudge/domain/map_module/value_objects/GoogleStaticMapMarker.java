package com.kbalazsworks.stackjudge.domain.map_module.value_objects;

import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerSizeEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // @todo3: do I need it?
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GoogleStaticMapMarker
{
    private MarkerSizeEnum  size;
    private MarkerColorEnum color;
    private String          label;
    private double          centerLat;
    private double          centerLng;
}
