package com.kbalazsworks.stackjudge.mocking;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.services.CdnService;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;

import static org.mockito.Mockito.mock;

public class MockCreation
{
    public static CdnService getCdnServiceMock()
    {
        return mock(CdnService.class);
    }

    public static UrlFactory getUrlFactoryMock()
    {
        return mock(UrlFactory.class);
    }

    public static StaticProxyService getStaticProxyServiceMock()
    {
        return mock(StaticProxyService.class);
    }

    public static PutObjectResult getPutObjectResultMock()
    {
        return mock(PutObjectResult.class);
    }
}
