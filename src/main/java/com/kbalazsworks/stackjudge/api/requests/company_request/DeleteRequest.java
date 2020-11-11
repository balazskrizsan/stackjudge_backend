package com.kbalazsworks.stackjudge.api.requests.company_request;

import javax.validation.constraints.Min;

public class DeleteRequest
{
    @Min(1)
    private long companyId;

    public void setCompanyId(long companyId)
    {
        this.companyId = companyId;
    }

    public long getCompanyId()
    {
        return companyId;
    }
}
