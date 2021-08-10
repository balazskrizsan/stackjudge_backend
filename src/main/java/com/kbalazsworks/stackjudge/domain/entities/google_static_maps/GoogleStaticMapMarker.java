package com.kbalazsworks.stackjudge.domain.entities.google_static_maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoogleStaticMapMarker
{
    private Short  size;
    private Short  color;
    private String label;
    private Double centerLat;
    private Double centerLng;
}
