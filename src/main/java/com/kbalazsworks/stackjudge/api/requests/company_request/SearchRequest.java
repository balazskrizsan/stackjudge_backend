package com.kbalazsworks.stackjudge.api.requests.company_request;

import java.util.List;

public record SearchRequest(Integer page, Integer limit, List<Short> requestRelationIds)
{
}
