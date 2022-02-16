package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public record CompanyCreateRequest(
    @JsonProperty("name")
    @Size(min = 2, max = 255, groups = ICompanyRequestValidationGroup.class)
    String name,

    @JsonProperty("domain")
    @Size(min = 3, max = 255, groups = ICompanyRequestValidationGroup.class)
    String domain,

    @JsonProperty("companySizeId")
    @Min(value = 1, groups = ICompanyRequestValidationGroup.class)
    @Max(value = 5, groups = ICompanyRequestValidationGroup.class)
    short companySizeId,

    @JsonProperty("itSizeId")
    @Min(value = 1, groups = ICompanyRequestValidationGroup.class)
    @Max(value = 5, groups = ICompanyRequestValidationGroup.class)
    short itSizeId
)
{
}
