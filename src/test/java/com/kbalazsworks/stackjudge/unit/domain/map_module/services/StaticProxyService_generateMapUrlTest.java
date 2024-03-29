package com.kbalazsworks.stackjudge.unit.domain.map_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleMapsUrlWithHash;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapMarkerFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.setup_mock.ApplicationPropertiesMocker;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.kbalazsworks.stackjudge.mocking.setup_mock.ApplicationPropertiesMocker.GOOGLE_MAPS_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaticProxyService_generateMapUrlTest extends AbstractTest
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
                new GoogleStaticMapFakeBuilder().build(),
                new ArrayList<>(),
                new GoogleMapsUrlWithHash(
                    "https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&scale=3&zoom=4&center=5.0,6.0&size=1x2&key=" + GOOGLE_MAPS_KEY,
                    "071a4a79f317fd1f6b913842e96703f1"
                )
            );
        }
        if (2 == repetition)
        {
            return new TestData(
                new GoogleStaticMapFakeBuilder().build(),
                new GoogleStaticMapMarkerFakeBuilder().buildAsList(),
                new GoogleMapsUrlWithHash(
                    "https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&scale=3&zoom=4&center=5.0,6.0&size=1x2&key=" + GOOGLE_MAPS_KEY + "&markers=size:mid%7Ccolor:brown%7Clabel:A%7C1.0,2.0",
                    "071a4a79f317fd1f6b913842e96703f1"
                )
            );
        }
        if (3 == repetition)
        {
            return new TestData(
                new GoogleStaticMapFakeBuilder().build(),
                new GoogleStaticMapMarkerFakeBuilder().buildAsListWithTwoItems(),
                new GoogleMapsUrlWithHash(
                    "https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&scale=3&zoom=4&center=5.0,6.0&size=1x2&key=" + GOOGLE_MAPS_KEY + "&markers=size:mid%7Ccolor:brown%7Clabel:A%7C1.0,2.0&markers=size:mid%7Ccolor:brown%7Clabel:A%7C1.0,2.0",
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
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        GoogleMapsUrlWithHash actualUrlWithHash = serviceFactory
            .getStaticProxyService(ApplicationPropertiesMocker.getDefaultMock())
            .generateMapUrl(testData.googleStaticMap(), testData.markers());

        // Assert
        assertThat(actualUrlWithHash).isEqualTo(testData.expectedUrlWithMap());
    }
}
