package com.kbalazsworks.stackjudge.api.requests.company_request;

import javax.validation.constraints.Min;

public class GetStackRecursiveByCompanyIdRequest
{
    @Min(1)
    private long companyId;

    public long getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(long companyId)
    {
        this.companyId = companyId;
    }
}
