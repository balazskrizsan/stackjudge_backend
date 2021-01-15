package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Review reviewTable =
        com.kbalazsworks.stackjudge.db.tables.Review.REVIEW;

    public void create(Review review)
    {
        createQueryBuilder()
            .insertInto(
                reviewTable,
                reviewTable.GROUP_ID,
                reviewTable.VISIBILITY,
                reviewTable.REVIEW_,
                reviewTable.RATE,
                reviewTable.CREATED_AT,
                reviewTable.CREATED_BY
            )
            .values(
                review.groupId(),
                review.visibility(),
                review.review(),
                review.rate(),
                review.createdAt(),
                review.createdBy()
            )
            .execute();
    }
}
