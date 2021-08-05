package com.kbalazsworks.stackjudge.api.requests.maps_requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

// @todo: try with record or immutable class
@Setter
@Getter
public class MarkerRequest
{
    @Min(1)
    @Max(3)
    private int size;

    @Min(1)
    @Max(2)
    private int color;

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double centerLat;

    @DecimalMin(value = "-180.0", inclusive = false)
    @DecimalMax(value = "180.0", inclusive = false)
    private Double centerLng;
}

