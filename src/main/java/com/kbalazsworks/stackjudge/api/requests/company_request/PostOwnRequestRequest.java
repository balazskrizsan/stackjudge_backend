package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record PostOwnRequestRequest(
    @JsonProperty("emailPart")
    @Size(min = 2, max = 50)
    String emailPart,

    @JsonProperty("companyId")
    @Positive
    long companyId
)
{
}
