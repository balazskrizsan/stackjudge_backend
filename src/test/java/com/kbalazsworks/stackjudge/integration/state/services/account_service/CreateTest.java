package com.kbalazsworks.stackjudge.integration.state.services.account_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.db.tables.records.UsersRecord;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class CreateTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @Commit
    @Ignore("JPA commit not working properly, no data saved to db")
    public void selectingFromFilledDb_returnsUserAndCallLogger()
    {
        // Arrange
        User testedUser       = new UserFakeBuilder().id(null).build();
        long actualFakeUserId = UserFakeBuilder.defaultId1;
        User expectedUser     = new UserFakeBuilder().build();

        // Act
        serviceFactory.getAccountService().create(testedUser);

        // Assert
        UsersRecord actualUser = getQueryBuilder()
            .selectFrom(usersTable)
            .where(usersTable.USERNAME.eq(expectedUser.getUsername()))
            .fetchOne();
        actualUser.setId(actualFakeUserId);

        assertThat(actualUser.into(User.class)).usingRecursiveComparison().isEqualTo(expectedUser);
    }
}
