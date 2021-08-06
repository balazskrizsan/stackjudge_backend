package com.kbalazsworks.stackjudge.api.requests.maps_requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

// @todo: try with record or immutable class
@Setter
@Getter
public class MarkerRequest
{
    @Min(1)
    @Max(3)
    private Short size;

    @Min(1)
    @Max(10)
    private Short color;

    @Pattern(regexp = "^[A-Z0-9]$")
    private String label;

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private double centerLat;

    @DecimalMin(value = "-180.0", inclusive = false)
    @DecimalMax(value = "180.0", inclusive = false)
    private double centerLng;
}

