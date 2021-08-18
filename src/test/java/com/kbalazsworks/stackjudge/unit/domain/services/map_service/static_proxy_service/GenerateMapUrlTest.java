package com.kbalazsworks.stackjudge.unit.domain.services.map_service.static_proxy_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleMapsUrlWithHash;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapMarkerFakeBuilder;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerateMapUrlTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private record TestData(
        GoogleStaticMap googleStaticMap,
        List<GoogleStaticMapMarker> markers,
        GoogleMapsUrlWithHash expectedUrlWithMap)
    {
    }

    @Test
    public void vintageHack()
    {
        assertTrue(true);
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(
                GoogleStaticMapFakeBuilder.build(),
                new ArrayList<>(),
                new GoogleMapsUrlWithHash(
                    "https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&scale=3&zoom=4&center=5.0,6.0&size=1x2&key=123123",
                    "071a4a79f317fd1f6b913842e96703f1"
                )
            );
        }
        if (2 == repetition)
        {
            return new TestData(
                GoogleStaticMapFakeBuilder.build(),
                GoogleStaticMapMarkerFakeBuilder.buildAsList(),
                new GoogleMapsUrlWithHash(
                    "https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&scale=3&zoom=4&center=5.0,6.0&size=1x2&key=123123&markers=size:mid%7Ccolor:brown%7Clabel:A%7C1.0,2.0",
                    "071a4a79f317fd1f6b913842e96703f1"
                )
            );
        }
        if (3 == repetition)
        {
            return new TestData(
                GoogleStaticMapFakeBuilder.build(),
                GoogleStaticMapMarkerFakeBuilder.buildAsListWithTwoItems(),
                new GoogleMapsUrlWithHash(
                    "https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&scale=3&zoom=4&center=5.0,6.0&size=1x2&key=123123&markers=size:mid%7Ccolor:brown%7Clabel:A%7C1.0,2.0&markers=size:mid%7Ccolor:brown%7Clabel:A%7C1.0,2.0",
                    "071a4a79f317fd1f6b913842e96703f1"
                )
            );
        }

        throw getRepeatException(repetition);
    }

    @RepeatedTest(3)
    public void getGeneratedUrlWithGeneratedHash_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData              testData                  = provider(repetitionInfo.getCurrentRepetition());
        ApplicationProperties applicationPropertiesMock = mock(ApplicationProperties.class);
        when(applicationPropertiesMock.getGoogleMapsKey()).thenReturn("123123");

        // Act
        GoogleMapsUrlWithHash actualUrlWithHash = serviceFactory
            .getStaticProxyService(applicationPropertiesMock)
            .generateMapUrl(testData.googleStaticMap(), testData.markers());

        // Assert
        assertThat(actualUrlWithHash).isEqualTo(testData.expectedUrlWithMap());
    }
}
