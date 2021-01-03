package com.kbalazsworks.stackjudge.api.requests.group_request;

import javax.validation.constraints.*;

public record GroupCreateRequest(
    @Positive
    long companyId,

    @Positive
    Long parentId,

    @Min(1)
    @Max(4)
    short typeId,

    @Size(min = 2, max = 255)
    String name,

    @Min(1)
    @Max(7)
    short membersOnStackId
)
{
}
