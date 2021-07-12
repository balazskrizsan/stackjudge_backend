package com.kbalazsworks.stackjudge.api.requests.company_request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class DeleteRequest
{
    @Min(1)
    private long companyId;
}
