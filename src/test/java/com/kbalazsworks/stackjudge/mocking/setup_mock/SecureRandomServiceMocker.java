package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import lombok.NonNull;

import static org.mockito.Mockito.when;

public class SecureRandomServiceMocker extends MockCreator
{
    public static SecureRandomService getUrlEncoded_returns_string(int whenLength, @NonNull String thanRandom)
    {
        SecureRandomService mock = getSecureRandomService();
        when(mock.getUrlEncoded(whenLength)).thenReturn(thanRandom);

        return mock;
    }
}
