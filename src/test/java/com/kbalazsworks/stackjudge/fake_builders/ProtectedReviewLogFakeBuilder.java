package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.ProtectedReviewLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class ProtectedReviewLogFakeBuilder
{
    public static Long defaultId1 = 105001L;

    private Long          id           = defaultId1;
    private long          viewerUserId = UserFakeBuilder.defaultId1;
    private long          reviewId     = ReviewFakeBuilder.defaultId1;
    private LocalDateTime createdAt    = MockFactory.testLocalDateTimeMock1;

    public ProtectedReviewLog build()
    {
        return new ProtectedReviewLog(id, viewerUserId, reviewId, createdAt);
    }
}
