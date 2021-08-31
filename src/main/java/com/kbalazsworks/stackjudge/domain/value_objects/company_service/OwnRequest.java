package com.kbalazsworks.stackjudge.domain.value_objects.company_service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OwnRequest(
    @JsonProperty long companyId,
    @JsonProperty String emailPart
)
{
}
