package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record ProtectedReviewLog(
    Long id,
    long viewerUserId,
    long reviewId,
    LocalDateTime createdAt
)
{
}
