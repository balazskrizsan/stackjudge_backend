package com.kbalazsworks.stackjudge.integration.state.services.account_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.db.tables.records.UsersRecord;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountService_CreateUserTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @TruncateAllTables
    @SneakyThrows
    public void selectingFromFilledDb_returnsUserAndCallLogger()
    {
        // Arrange
        User testedUserWithIdsId = new UserFakeBuilder().build();
        User expectedIdsUser     = new UserFakeBuilder().build();

        // Act
        serviceFactory.getAccountService().createUser(testedUserWithIdsId);

        // Assert
        UsersRecord actualUser = getQueryBuilder().selectFrom(usersTable).fetchOne();

        assertThat(actualUser.into(User.class)).usingRecursiveComparison().isEqualTo(expectedIdsUser);
    }
}
