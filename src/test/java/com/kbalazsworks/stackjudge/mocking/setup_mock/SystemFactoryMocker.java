package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.common_module.factories.SystemFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import static org.mockito.Mockito.when;

public class SystemFactoryMocker
{
    public static SystemFactory getCurrentTimeMillis_returns_multiCalledLongs(long thenReturn, Long... thenReturnMore)
    {
        SystemFactory mock = MockCreator.getSystemFactoryMock();
        when(mock.getCurrentTimeMillis()).thenReturn(thenReturn, thenReturnMore);

        return mock;
    }
}
