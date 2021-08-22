package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class AccountServiceMocker extends MockCreator
{
    public static AccountService findByUserIdsWithIdMap_returns_mappedUsers(
        List<Long> whenUsersIds,
        Map<Long, User> thanUsersMap
    )
    {
        AccountService mock = getAccountServiceMock();
        when(mock.findByIdsWithIdMap(whenUsersIds)).thenReturn(thanUsersMap);

        return mock;
    }
}
