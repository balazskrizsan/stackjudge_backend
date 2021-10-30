package com.kbalazsworks.stackjudge.domain.company_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroupTree;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
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
    @JsonProperty CompanyOwners companyOwners,
    @JsonProperty Map<Long, User> companyUsers
)
{
}
