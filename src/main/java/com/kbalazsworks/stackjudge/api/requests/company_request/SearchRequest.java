package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.kbalazsworks.stackjudge.api.enums.SearchLimitEnum;

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
        if (SearchLimitEnum.getByValue(limit) == null)
        {
            return SearchLimitEnum.DEFAULT.getValue();
        }

        return limit;
    }
}
