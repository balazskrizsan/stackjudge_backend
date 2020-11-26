package com.kbalazsworks.stackjudge.integration.domain.services.group_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.GroupRecord;
import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class GroupServiceCreateTest extends AbstractIntegrationTest
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
    public void addANewRecord_perfect()
    {
        // Arrange
        Long  testedGroupId = 843L;
        Group testedGroup   = new GroupFakeBuilder().build();
        Group expectedGroup = new GroupFakeBuilder().build();

        // Act
        groupService.create(testedGroup);

        // Assert
        GroupRecord actualGroup = getQueryBuilder().selectFrom(groupTable).fetchOne();
        actualGroup.setId(testedGroupId);

        Assert.assertEquals(actualGroup.into(Group.class), expectedGroup);
    }
}
