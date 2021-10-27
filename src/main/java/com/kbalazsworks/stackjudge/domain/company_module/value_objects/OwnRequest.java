package com.kbalazsworks.stackjudge.domain.company_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OwnRequest(
    @JsonProperty long companyId,
    @JsonProperty String emailPart
)
{
}
