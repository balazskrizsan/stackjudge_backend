package com.kbalazsworks.stackjudge.api.requests.maps_requests;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record GoogleStaticMapsRequest(
    @Min(50)
    @Max(2000)
    int sizeX,

    @Min(50)
    @Max(2000)
    int sizeY,

    @Min(1)
    @Max(2)
    short scale,

    @Min(1)
    @Max(21)
    short zoom,

    @Min(1)
    @Max(4)
    short mapType,

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    Double centerLat,

    @DecimalMin(value = "-180.0", inclusive = false)
    @DecimalMax(value = "180.0", inclusive = false)
    Double centerLng
)
{
}
