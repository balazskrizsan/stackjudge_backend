package com.kbalazsworks.stackjudge.domain.persistance_log_module.entities;

import java.time.LocalDateTime;

public record ProtectedReviewLog(
    Long id,
    String viewerIdsUserId,
    long reviewId,
    LocalDateTime createdAt
)
{
}
