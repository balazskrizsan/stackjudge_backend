package com.kbalazsworks.stackjudge.api.requests.company_request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public record CompanyCreateRequest(
    @Size(min = 2, max = 255, groups = ICompanyRequestValidationGroup.class)
    String  name,

    @Min( value = 1, groups = ICompanyRequestValidationGroup.class)
    @Max( value = 5, groups = ICompanyRequestValidationGroup.class)
    short companySizeId,

    @Min( value = 1, groups = ICompanyRequestValidationGroup.class)
    @Max( value = 5, groups = ICompanyRequestValidationGroup.class)
    short itSizeId
)
{
}
