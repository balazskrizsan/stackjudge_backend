package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreation;
import lombok.SneakyThrows;

import java.net.URL;

import static com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder.fakeGoogleMapsUrl;
import static org.mockito.Mockito.when;

public class UrlFactoryMocks extends MockCreation
{
    public static UrlFactory create_returns_URL(String whenUrl)
    {
        return create_returns_URL(whenUrl, fakeGoogleMapsUrl);
    }

    @SneakyThrows
    public static UrlFactory create_returns_URL(String whenUrl, String thanUrl)
    {
        UrlFactory urlFactoryMock = getUrlFactoryMock();
        when(urlFactoryMock.create(whenUrl)).thenReturn(new URL(thanUrl));

        return urlFactoryMock;
    }
}
