package com.kbalazsworks.stackjudge.integration.domain.services.review_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.ReviewRecord;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.review_module.services.ReviewService;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class ReviewServiceCreateTest extends AbstractIntegrationTest
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
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql",
                    "classpath:test/sqls/preset_add_1_group.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void addNewReview_perfect()
    {
        // Arrange
        Review testedReview   = new ReviewFakeBuilder().build();
        Review expectedReview = new ReviewFakeBuilder().build();

        // Act
        reviewService.create(testedReview);

        // Assert
        ReviewRecord actualReview = getQueryBuilder().selectFrom(reviewTable).fetchOne();
        actualReview.setId(testedReview.id());

        assertThat(actualReview.into(Review.class)).isEqualTo(expectedReview);
    }
}
