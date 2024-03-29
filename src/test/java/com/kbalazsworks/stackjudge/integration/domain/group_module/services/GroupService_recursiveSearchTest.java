package com.kbalazsworks.stackjudge.integration.domain.group_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.RecursiveGroupFakeBuilder;
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

public class GroupService_recursiveSearchTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private record TestData(List<Long> testedCompanyId, List<RecursiveGroup> expectedList)
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(List.of(), List.of());
        }
        if (2 == repetition)
        {
            return new TestData(List.of(CompanyFakeBuilder.defaultId1), new RecursiveGroupFakeBuilder().buildAsList());
        }

        throw getRepeatException(repetition);
    }

    @RepeatedTest(2)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_3_companies.sql",
                    "classpath:test/sqls/preset_add_10_address.sql",
                    "classpath:test/sqls/preset_add_10_groups.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void recursiveResult_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData tD = provider(repetitionInfo.getCurrentRepetition());

        // Act
        List<RecursiveGroup> actualList = serviceFactory.getGroupService().recursiveSearch(tD.testedCompanyId);

        // Assert
        assertThat(actualList).usingRecursiveComparison().isEqualTo(tD.expectedList);
    }
}
