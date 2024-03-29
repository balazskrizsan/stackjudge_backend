package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;

import java.util.ArrayList;
import java.util.List;

public class GoogleStaticMapMarkerFakeBuilder
{
    private static final MarkerSizeEnum  size      = MarkerSizeEnum.MID;
    private static final MarkerColorEnum color     = MarkerColorEnum.BROWN;
    private static final String          label     = "A";
    private static final double          centerLat = 1;
    private static final double          centerLng = 2;

    public List<GoogleStaticMapMarker> buildAsList()
    {
        return new ArrayList<>()
        {{
            add(build());
        }};
    }

    public List<GoogleStaticMapMarker> buildAsListWithTwoItems()
    {
        return new ArrayList<>()
        {{
            add(build());
            add(build());
        }};
    }

    public GoogleStaticMapMarker build()
    {
        return new GoogleStaticMapMarker(size, color, label, centerLat, centerLng);
    }
}
