package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleMapsUrlWithHash;

public class GoogleMapsUrlWithHashFakeBuilder
{
    public static final String fakeGoogleMapsUrl = "https://maps.google.com/uri/for/custom/map";
    public static final String fakeUrlHash       = GoogleStaticMapsCacheFakeBuilder.hash;

    public static GoogleMapsUrlWithHash build()
    {
        return new GoogleMapsUrlWithHash(fakeGoogleMapsUrl, fakeUrlHash);
    }
}
