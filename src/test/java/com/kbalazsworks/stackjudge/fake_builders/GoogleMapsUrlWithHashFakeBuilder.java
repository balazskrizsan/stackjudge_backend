package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleMapsUrlWithHash;

public class GoogleMapsUrlWithHashFakeBuilder
{
    public static GoogleMapsUrlWithHash build()
    {
        return new GoogleMapsUrlWithHash(
            GoogleStaticMapsCacheFakeBuilder.fileName,
            GoogleStaticMapsCacheFakeBuilder.hash
        );
    }
}
