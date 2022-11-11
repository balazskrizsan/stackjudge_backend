package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.NonNull;

import static org.mockito.Mockito.when;

public class ApplicationPropertiesMocker extends MockCreator
{
    public static final String GOOGLE_MAPS_KEY    = "aws-s3-cdn-bucket";
    public static final String SITE_DOMAIN        = "dev.stackjudge.com";
    public static final String SITE_FRONTEND_HOST = "http://stackjudge.com";

    public static @NonNull ApplicationProperties getDefaultMock()
    {
        ApplicationProperties mock = getApplicationPropertiesMock();

        when(mock.getGoogleMapsKey()).thenReturn(GOOGLE_MAPS_KEY);
        when(mock.getSiteDomain()).thenReturn(SITE_DOMAIN);
        when(mock.getSiteFrontendHost()).thenReturn(SITE_FRONTEND_HOST);

        return mock;
    }
}
