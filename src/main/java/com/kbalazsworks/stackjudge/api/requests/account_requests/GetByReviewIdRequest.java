package com.kbalazsworks.stackjudge.api.requests.account_requests;

import javax.validation.constraints.Min;

public class GetByReviewIdRequest
{
    @Min(1)
    private long reviewId;

    public void setReviewId(long reviewId)
    {
        this.reviewId = reviewId;
    }

    public long getReviewId()
    {
        return reviewId;
    }
}
