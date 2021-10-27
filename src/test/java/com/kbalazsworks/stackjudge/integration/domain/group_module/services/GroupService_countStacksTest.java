package com.kbalazsworks.stackjudge.integration.domain.group_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class GroupService_countStacksTest extends AbstractIntegrationTest
{
    @Autowired
    private GroupService groupService;

    @Test
    @TruncateAllTables
    public void countOnEmptyTable_returnsWithEmptyList()
    {
        // Arrange
        List<Long>         testedIds        = List.of(1L, 3L);
        Map<Long, Integer> expectedResponse = new HashMap<>();

        // Act
        Map<Long, Integer> actualResponse = groupService.countStacks(testedIds);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
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
    public void countOnFilledDb_returnWithTheExpectedList()
    {
        // Arrange
        List<Long>         testedCompanyIds = List.of(CompanyFakeBuilder.defaultId1, CompanyFakeBuilder.defaultId3);
        Map<Long, Integer> expectedResponse = Map.of(CompanyFakeBuilder.defaultId1, 2, CompanyFakeBuilder.defaultId3, 3);

        // Act
        Map<Long, Integer> actualResponse = groupService.countStacks(testedCompanyIds);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
