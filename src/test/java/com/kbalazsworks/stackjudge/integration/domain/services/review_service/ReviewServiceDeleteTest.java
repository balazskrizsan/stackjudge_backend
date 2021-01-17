package com.kbalazsworks.stackjudge.integration.domain.services.review_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class ReviewServiceDeleteTest extends AbstractIntegrationTest
{
    @Autowired
    private ReviewService reviewService;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_3_companies.sql",
                    "classpath:test/sqls/preset_add_10_groups.sql",
                    "classpath:test/sqls/preset_add_10_reviews.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void deletingTheInsertedFields_willBeDeleted()
    {
        // Arrange
        long testedCompanyId = 3456232;

        // Act
        reviewService.delete(testedCompanyId);

        // Assert
        Review actualReview = getQueryBuilder()
            .selectFrom(reviewTable)
            .where(reviewTable.ID.eq(testedCompanyId))
            .fetchOneInto(Review.class);

        assertThat(actualReview).isNull();
    }
}
