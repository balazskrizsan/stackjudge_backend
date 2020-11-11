package com.kbalazsworks.stackjudge.api.requests.stack_request;

import javax.validation.constraints.*;

public record StackCreateRequest(
    @Positive
    long companyId,

    @Positive
    Long parentId,

    @Min(1)
    @Max(2)
    short typeId,

    @Size(min = 2, max = 255)
    String name,

    @Min(1)
    @Max(7)
    short membersOnStackId
)
{
}
