package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;

import static org.mockito.Mockito.when;

public class StaticProxyServiceMocks extends MockCreator
{
    public static StaticProxyService generateMapUrl_returns_GoogleMapsUrlWithHash(
        GoogleStaticMap whenGoogleStaticMap,
        List<GoogleStaticMapMarker> whenMarkers
    )
    {
        StaticProxyService staticProxyServiceMock = getStaticProxyServiceMock();
        when(staticProxyServiceMock.generateMapUrl(whenGoogleStaticMap, whenMarkers))
            .thenReturn(GoogleMapsUrlWithHashFakeBuilder.build());

        return staticProxyServiceMock;
    }
}
