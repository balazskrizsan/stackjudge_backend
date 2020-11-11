package com.kbalazsworks.stackjudge.api.requests.company_request;

import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;

public record CompanyCompositeRequest(
    @Valid
    @ConvertGroup(to = ICompanyRequestValidationGroup.class)
    CompanyCreateRequest company,

    @Valid
    @ConvertGroup(to = IAddressRequestValidationGroup.class)
    AddressCreateRequest address
)
{
}
