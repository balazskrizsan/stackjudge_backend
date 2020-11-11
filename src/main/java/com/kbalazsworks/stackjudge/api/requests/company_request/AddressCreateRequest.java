package com.kbalazsworks.stackjudge.api.requests.company_request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record AddressCreateRequest(
    @Size(min = 10, max = 255, groups = IAddressRequestValidationGroup.class)
    String fullAddress,

    @Pattern(regexp = "^\\d{1,9}\\.\\d{1,20}$")
    BigDecimal markerLat,

    @Pattern(regexp = "^\\d{1,9}\\.\\d{1,20}$")
    BigDecimal markerLng,

    @Pattern(regexp = "^\\d{1,9}\\.\\d{1,20}$")
    BigDecimal manualMarkerLat,

    @Pattern(regexp = "^\\d{1,9}\\.\\d{1,20}$")
    BigDecimal manualMarkerLng
)
{
}
