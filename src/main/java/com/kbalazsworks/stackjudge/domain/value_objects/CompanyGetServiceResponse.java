package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.maps_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.state.entities.User;

import java.util.List;
import java.util.Map;

public record CompanyGetServiceResponse(
    @JsonProperty Company company,
    @JsonProperty CompanyStatistic companyStatistic,
    @JsonProperty List<RecursiveGroupTree> companyGroups,
    @JsonProperty CompanyAddresses companyAddresses,
    @JsonProperty Map<Long, Map<MapPositionEnum, StaticMapResponse>> companyAddressMaps,
    @JsonProperty Map<Long, List<Review>> companyReviews,
    @JsonProperty CompanyOwners flatCompanyOwners,
    @JsonProperty Map<Long, User> companyUsers
)
{
}
