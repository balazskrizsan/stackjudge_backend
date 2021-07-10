package com.kbalazsworks.stackjudge.api.requests.account_requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class GetByReviewIdRequest
{
    @Min(1)
    private long reviewId;
}
