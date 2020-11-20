package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.kbalazsworks.stackjudge.api.enums.SearchLimitEnum;

import java.util.List;

public record SearchRequest(Integer page, Short limit, List<Short> requestRelationIds)
{
    public Integer page()
    {
        if (page == null) {
            return 0;
        }

        return page;
    }

    public Short limit()
    {
        if (SearchLimitEnum.getByValue(limit) == null)
        {
            return  SearchLimitEnum.DEFAULT.getValue();
        }

        return limit;
    }
}
