package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Company;

import java.util.List;
import java.util.Map;

public record CompanySearchServiceResponse(
    @JsonProperty List<Company> companies,
    @JsonProperty List<Map<Long, CompanyStatistic>> companyStatistics
)
{
}
