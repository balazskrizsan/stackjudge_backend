package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;

import java.util.List;

public record CompanyGetServiceResponse(
    @JsonProperty Company company,
    @JsonProperty CompanyStatistic companyStatistic,
    @JsonProperty List<RecursiveGroupTree> companyGroups,
    @JsonProperty List<Address> companyAddresses
)
{
}
