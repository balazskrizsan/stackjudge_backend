package com.kbalazsworks.stackjudge.domain.services.map_service;

import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMaps;
import com.kbalazsworks.stackjudge.domain.entities.google_static_maps.GoogleMapsMarker;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticProxyService
{
    public final ApplicationProperties applicationProperties;

    // @todo: test
    public String generateMapUrl(GoogleStaticMaps googleStaticMap, List<GoogleMapsMarker> markers)
    {
        UriComponentsBuilder mapsUrlBuilder = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("maps.googleapis.com")
            .path("/maps/api/staticmap")
            .queryParam("maptype", googleStaticMap.mapType())
            .queryParam("scale", googleStaticMap.scale())
            .queryParam("zoom", googleStaticMap.zoom())
            .queryParam("center", googleStaticMap.centerLat() + "," + googleStaticMap.centerLng())
            .queryParam("size", googleStaticMap.sizeX() + "x" + googleStaticMap.sizeY())
            .queryParam("key", applicationProperties.getGoogleMapsKey());

        for (GoogleMapsMarker m : markers)
        {
            String markerValue = "";

            if (null != m.getSize())
            {
                markerValue += "size:" + MarkerSizeEnum.getByValue(m.getSize()).toString().toLowerCase();
            }
            if (null != m.getColor())
            {
                markerValue += "%7Ccolor:" + MarkerColorEnum.getByValue(m.getColor()).toString().toLowerCase();
            }
            if (null != m.getLabel())
            {
                markerValue += "%7Clabel:" + m.getLabel();
            }
            markerValue += "%7C" + m.getCenterLat() + "," + m.getCenterLng();

            mapsUrlBuilder = mapsUrlBuilder.queryParam("markers", markerValue);
        }

        return mapsUrlBuilder.build().toUriString();
    }
}
