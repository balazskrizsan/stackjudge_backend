package com.kbalazsworks.stackjudge.api.requests.company_request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
public class GetRequest
{
    @Min(1)
    private long        companyId;
    private List<Short> requestRelationIds;
}
