package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.state.entities.User;

import java.util.List;
import java.util.Map;

public record CompanyGetServiceResponse(
    @JsonProperty Company company,
    @JsonProperty CompanyStatistic companyStatistic,
    @JsonProperty List<RecursiveGroupTree> companyGroups,
    @JsonProperty List<Address> companyAddresses,
    @JsonProperty Map<Long, Map<MapPositionEnum, StaticMapResponse>> companyAddressMaps,
    @JsonProperty Map<Long, List<Review>> companyReviews,
    @JsonProperty List<Long> companyOwners,
    @JsonProperty Map<Long, User> companyUsers
)
{
}
