package com.kbalazsworks.stackjudge.integration.state.services.account_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class FindByIdsTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private record TestData(List<Long> testedUserIds, List<User> expectedUsers)
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(
                List.of(),
                List.of()
            );
        }

        if (2 == repetition)
        {
            return new TestData(
                List.of(UserFakeBuilder.defaultId1),
                List.of(new UserFakeBuilder().build())
            );
        }

        if (3 == repetition)
        {
            return new TestData(
                List.of(UserFakeBuilder.defaultId1, UserFakeBuilder.defaultId2),
                List.of(new UserFakeBuilder().build(), new UserFakeBuilder().build2())
            );
        }

        throw getRepeatException(repetition);
    }

    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_2_user.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @RepeatedTest(3)
    public void selectingFromFilledDb_returnsUses(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData tD = provider(repetitionInfo.getCurrentRepetition());

        // Act
        List<User> actualUser = serviceFactory.getAccountService().findByIds(tD.testedUserIds);

        // Assert
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(tD.expectedUsers);
    }
}
