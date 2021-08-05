package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.kbalazsworks.stackjudge.api.request_enums.CompanySearchLimitEnum;

import java.util.List;

public record SearchRequest(Integer seekId, Short limit, List<Short> requestRelationIds, Short navigationId)
{
    public Integer seekId()
    {
        if (seekId == null)
        {
            return 0;
        }

        return seekId;
    }

    public Short limit()
    {
        if (CompanySearchLimitEnum.getByValue(limit) == null)
        {
            return CompanySearchLimitEnum.DEFAULT.getValue();
        }

        return limit;
    }
}
