package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Company;

public record CompanyGetServiceResponse(
    @JsonProperty Company company,
    @JsonProperty CompanyStatistic companyStatistic
)
{
}
