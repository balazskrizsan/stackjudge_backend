package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record AddressCreateRequest(
    @JsonProperty("fullAddress")
    @Size(min = 10, max = 255, groups = IAddressRequestValidationGroup.class)
    String fullAddress,

    @JsonProperty("markerLat")
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,20}$")
    Double markerLat,

    @JsonProperty("markerLng")
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,20}$")
    Double markerLng,

    @JsonProperty("manualMarkerLat")
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,20}$")
    Double manualMarkerLat,

    @JsonProperty("manualMarkerLng")
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,20}$")
    Double manualMarkerLng
)
{
}
