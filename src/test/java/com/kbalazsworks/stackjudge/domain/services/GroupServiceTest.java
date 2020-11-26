package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.db.tables.records.GroupRecord;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.domain.fakes.GroupFakeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class GroupServiceTest extends AbstractIntegrationTest
{
    @Autowired
    private GroupService groupService;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_one_company.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void create_addANewRecord_perfect()
    {
        // Arrange
        Long  testedGroupId = 843L;
        Group testedGroup   = new GroupFakeBuilder().build();
        Group expectedGroup = new GroupFakeBuilder().build();

        // Act
        groupService.create(testedGroup);

        // Assert
        GroupRecord actualGroup = getQueryBuilder()
            .selectFrom(groupTable)
            .fetchOne();
        actualGroup.setId(testedGroupId);

        Assert.assertEquals(actualGroup.into(Group.class), expectedGroup);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void countStacks_countOnEmptyTable_returnsWithEmptyList()
    {
        // Arrange
        List<Long>         testedIds        = Arrays.asList(1L, 3L);
        Map<Long, Integer> expectedResponse = new HashMap<>();

        // Act
        Map<Long, Integer> actualResponse = groupService.countStacks(testedIds);

        // Assert
        Assert.assertEquals(actualResponse, expectedResponse);
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
    public void countStacks_countOnFilledDb_returnWithTheExpectedList()
    {
        // Arrange
        List<Long> testedIds = Arrays.asList(164985367L, 854621354L);
        Map<Long, Integer> expectedResponse = new HashMap<>()
        {{
            put(164985367L, 2);
            put(854621354L, 3);
        }};

        // Act
        Map<Long, Integer> actualResponse = groupService.countStacks(testedIds);

        // Assert
        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void countTeams_countOnEmptyTable_returnsWithEmptyList()
    {
        // Arrange
        List<Long>         testedIds        = Arrays.asList(1L, 3L);
        Map<Long, Integer> expectedResponse = new HashMap<>();

        // Act
        Map<Long, Integer> actualResponse = groupService.countTeams(testedIds);

        // Assert
        Assert.assertEquals(actualResponse, expectedResponse);
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
    public void countTeams_countOnFilledDb_returnWithTheExpectedList()
    {
        // Arrange
        List<Long> testedIds = Arrays.asList(164985367L, 854621354L);
        Map<Long, Integer> expectedResponse = new HashMap<>()
        {{
            put(164985367L, 1);
            put(854621354L, 2);
        }};

        // Act
        Map<Long, Integer> actualResponse = groupService.countTeams(testedIds);

        // Assert
        Assert.assertEquals(expectedResponse, actualResponse);
    }
}
