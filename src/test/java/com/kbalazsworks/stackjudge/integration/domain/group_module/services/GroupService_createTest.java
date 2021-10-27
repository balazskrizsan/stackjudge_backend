package com.kbalazsworks.stackjudge.integration.domain.group_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.GroupRecord;
import com.kbalazsworks.stackjudge.domain.group_module.entities.Group;
import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class GroupService_createTest extends AbstractIntegrationTest
{
    @Autowired
    private GroupService groupService;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql"
                }
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
        Group testedGroup   = new GroupFakeBuilder().build();
        Group expectedGroup = new GroupFakeBuilder().build();

        // Act
        groupService.create(testedGroup);

        // Assert
        GroupRecord actualGroup = getQueryBuilder().selectFrom(groupTable).fetchOne();
        actualGroup.setId(testedGroup.id());

        assertThat(actualGroup.into(Group.class)).isEqualTo(expectedGroup);
    }
}
