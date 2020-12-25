package com.kbalazsworks.stackjudge.integration.domain.mock_factories;

import com.kbalazsworks.stackjudge.domain.services.CdnService;

import static org.mockito.Mockito.mock;

public class CdnServiceMockFactory
{
    public static CdnService createMock()
    {
        return mock(CdnService.class);
    }
}
