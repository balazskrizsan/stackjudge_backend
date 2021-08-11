package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;

import java.util.ArrayList;
import java.util.List;

public class GoogleStaticMapMarkerFakeBuilder
{
    private static final MarkerSizeEnum  size      = MarkerSizeEnum.MID;
    private static final MarkerColorEnum color     = MarkerColorEnum.BROWN;
    private static final String          label     = "A";
    private static final double          centerLat = 1;
    private static final double          centerLng = 2;

    public static List<GoogleStaticMapMarker> buildAsList()
    {
        return new ArrayList<>()
        {{
            add(build());
        }};
    }

    public static List<GoogleStaticMapMarker> buildAsListWithTwoItems()
    {
        return new ArrayList<>()
        {{
            add(build());
            add(build());
        }};
    }

    public static GoogleStaticMapMarker build()
    {
        return new GoogleStaticMapMarker(size, color, label, centerLat, centerLng);
    }
}
