package com.kbalazsworks.stackjudge.integration.state.services.account_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.kbalazsworks.stackjudge.MockFactory.TEST_STATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class GetByReviewIdTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_user.sql",
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
    public void selectingFromFilledDb_returnsUserAndCallLogger()
    {
        // Arrange
        long  testedReviewId = ReviewFakeBuilder.defaultId1;
        User  expectedUser   = new UserFakeBuilder().build();
        State expectedState  = TEST_STATE;
        ProtectedReviewLog expectedProtectedReviewLog = new ProtectedReviewLog(
            null,
            expectedUser.getId(),
            testedReviewId,
            TEST_STATE.now()
        );

        ProtectedReviewLogService protectedReviewLogServiceMock = MockCreator.getProtectedReviewLogServiceMock();

        // Act
        User actualUser = serviceFactory.getAccountService(
            null,
            protectedReviewLogServiceMock
        )
            .getByReviewId(testedReviewId, TEST_STATE);

        // Assert
        assertAll(
            () -> verify(protectedReviewLogServiceMock, only()).create(expectedProtectedReviewLog, expectedState),
            () -> assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser)
        );
    }
}