package com.kbalazsworks.stackjudge.domain.company_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyStatistic(
    @JsonProperty long companyId,
    @JsonProperty int stackCount,
    @JsonProperty int teamsCount,
    @JsonProperty int reviewCount,
    @JsonProperty int technologiesCount
)
{
}
