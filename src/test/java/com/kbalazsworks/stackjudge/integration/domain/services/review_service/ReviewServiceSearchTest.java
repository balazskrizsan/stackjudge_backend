package com.kbalazsworks.stackjudge.integration.domain.services.review_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class ReviewServiceSearchTest extends AbstractIntegrationTest
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
                    "classpath:test/sqls/preset_add_1_group.sql",
                    "classpath:test/sqls/preset_add_1_review.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void checkAllDbFieldReturned_prefect()
    {
        // Arrange
        long       testedCompanyId  = CompanyFakeBuilder.defaultId1;
        long       testedGroupId    = GroupFakeBuilder.defaultId1;
        List<Long> testedCompanyIds = List.of(testedCompanyId);
        Review     expectedReview   = new ReviewFakeBuilder().build();

        // Act
        Map<Long, Map<Long, List<Review>>> actualReviews = reviewService.search(testedCompanyIds);

        // Assert
        assertThat(actualReviews.get(testedCompanyId).get(testedGroupId).get(0)).isEqualTo(expectedReview);
    }

    private record TestData(List<Long> testedIds, Map<Long, Map<Long, List<Long>>> expectedReviewIds)
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(List.of(123L), new HashMap<>());
        }

        if (2 == repetition)
        {
            return new TestData(
                List.of(CompanyFakeBuilder.defaultId1),
                Map.of(CompanyFakeBuilder.defaultId1, Map.of(
                    GroupFakeBuilder.defaultId1,
                    List.of(ReviewFakeBuilder.defaultId2, ReviewFakeBuilder.defaultId1)
                ))
            );
        }

        if (3 == repetition)
        {
            return new TestData(
                List.of(CompanyFakeBuilder.defaultId1, CompanyFakeBuilder.defaultId2),
                Map.of(
                    CompanyFakeBuilder.defaultId1,
                    Map.of(
                        GroupFakeBuilder.defaultId1,
                        List.of(ReviewFakeBuilder.defaultId2, ReviewFakeBuilder.defaultId1)
                    ),
                    CompanyFakeBuilder.defaultId2,
                    Map.of(
                        GroupFakeBuilder.defaultId4,
                        List.of(ReviewFakeBuilder.defaultId4, ReviewFakeBuilder.defaultId3),
                        GroupFakeBuilder.defaultId5,
                        List.of(ReviewFakeBuilder.defaultId5)
                    )
                )
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(3)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_3_companies.sql",
                    "classpath:test/sqls/preset_add_10_address.sql",
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
    public void selectTheDatabase_resultByProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        Map<Long, Map<Long, List<Long>>> actualReviewIdsResponse = new HashMap<>();
        reviewService.search(testData.testedIds).forEach(
            (companyId, reviewGroups) ->
            {
                Map<Long, List<Long>> groups = new HashMap<>();

                reviewGroups.forEach(
                    (groupId, group) -> groups.put(groupId, group.stream().map(Review::id).collect(Collectors.toList()))
                );

                actualReviewIdsResponse.put(companyId, groups);
            }
        );

        // Assert
        assertThat(actualReviewIdsResponse).isEqualTo(testData.expectedReviewIds);
    }
}
