package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.review_module.entities.DataProtectedReview;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class DataProtectedReviewFakeBuilder
{
    public static Long defaultViewerUserId = 123L;

    private Long viewerUserId = defaultViewerUserId;

    public DataProtectedReview build()
    {
        return new DataProtectedReview(viewerUserId);
    }
}
