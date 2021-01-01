package com.kbalazsworks.stackjudge.api.requests.company_request;

import javax.validation.constraints.Min;
import java.util.List;

public class GetRequest
{
    @Min(1)
    private long        companyId;
    private List<Short> requestRelationIds;

    public void setCompanyId(long companyId)
    {
        this.companyId = companyId;
    }

    public long getCompanyId()
    {
        return companyId;
    }

    public void setRequestRelationIds(List<Short> requestRelationIds)
    {
        this.requestRelationIds = requestRelationIds;
    }

    public List<Short> getRequestRelationIds()
    {
        return requestRelationIds;
    }
}
