package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.ProtectedReviewLog;
import org.springframework.stereotype.Repository;

@Repository
public class ProtectedReviewLogRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.ProtectedReviewLog protectedReviewLogTable =
        com.kbalazsworks.stackjudge.db.tables.ProtectedReviewLog.PROTECTED_REVIEW_LOG;

    public void create(ProtectedReviewLog protectedReviewLog)
    {
        getQueryBuilder()
            .insertInto(
                protectedReviewLogTable,
                protectedReviewLogTable.VIEWER_USER_ID,
                protectedReviewLogTable.REVIEW_ID,
                protectedReviewLogTable.CREATED_AT
            )
            .values(
                protectedReviewLog.viewerUserId(),
                protectedReviewLog.reviewId(),
                protectedReviewLog.createdAt()
            )
            .execute();
    }
}
