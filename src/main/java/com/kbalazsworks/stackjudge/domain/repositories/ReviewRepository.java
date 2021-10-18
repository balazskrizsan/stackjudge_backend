package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Review;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Review reviewTable =
        com.kbalazsworks.stackjudge.db.tables.Review.REVIEW;

    private final com.kbalazsworks.stackjudge.db.tables.Group groupTable =
        com.kbalazsworks.stackjudge.db.tables.Group.GROUP;

    public void create(@NonNull Review review)
    {
        getQueryBuilder()
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

    public Map<Long, List<Review>> search(List<Long> companiesIds)
    {
        return getQueryBuilder()
            .select(groupTable.COMPANY_ID)
            .select(reviewTable.fields())
            .from(reviewTable)
            .leftJoin(groupTable)
            .on(groupTable.ID.eq(reviewTable.GROUP_ID))
            .where(groupTable.COMPANY_ID.in(companiesIds))
            .orderBy(reviewTable.CREATED_AT.desc())
            .fetchGroups(groupTable.COMPANY_ID, r -> r.into(reviewTable.fields()).into(Review.class));
    }

    public void delete(long companyId)
    {
        getQueryBuilder()
            .deleteFrom(reviewTable)
            .where(reviewTable.ID.eq(companyId))
            .execute();
    }
}
