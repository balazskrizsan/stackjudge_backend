package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Review;

import java.util.List;
import java.util.Map;

public record CompanySearchServiceResponse(
    @JsonProperty List<Company> companies,
    @JsonProperty Map<Long, List<RecursiveGroupTree>> companyGroups,
    @JsonProperty List<PaginatorItem> paginator,
    @JsonProperty Long newSeekId,
    @JsonProperty Map<Long, CompanyStatistic> companyStatistics,
    @JsonProperty Map<Long, List<Address>> companyAddresses,
    @JsonProperty Map<Long, Map<Long, List<Review>>> companyReviews
)
{
}
