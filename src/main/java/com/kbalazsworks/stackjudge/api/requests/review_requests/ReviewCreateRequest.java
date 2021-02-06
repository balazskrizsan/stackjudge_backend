package com.kbalazsworks.stackjudge.api.requests.review_requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record ReviewCreateRequest(
    @Positive
    long group_id,

    @Min(1)
    @Max(3)
    short visibility,

    @Min(1)
    @Max(10)
    short rate,

    @Size(min = 2, max = 5000)
    String review
)
{
}
