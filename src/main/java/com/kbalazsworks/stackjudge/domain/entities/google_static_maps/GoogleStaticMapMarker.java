package com.kbalazsworks.stackjudge.domain.entities.google_static_maps;

import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerSizeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoogleStaticMapMarker
{
    private MarkerSizeEnum  size;
    private MarkerColorEnum color;
    private String          label;
    private Double          centerLat;
    private Double          centerLng;
}
