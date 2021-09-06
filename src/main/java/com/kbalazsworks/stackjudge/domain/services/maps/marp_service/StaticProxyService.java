package com.kbalazsworks.stackjudge.domain.services.maps.marp_service;

import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleMapsUrlWithHash;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticProxyService
{
    public final ApplicationProperties applicationProperties;

    public GoogleMapsUrlWithHash generateMapUrl(GoogleStaticMap googleStaticMap, List<GoogleStaticMapMarker> markers)
    {
        UriComponentsBuilder mapsUrlBuilder = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("maps.googleapis.com")
            .path("/maps/api/staticmap")
            .queryParam("maptype", googleStaticMap.mapType().toString().toLowerCase())
            .queryParam("scale", googleStaticMap.scale())
            .queryParam("zoom", googleStaticMap.zoom())
            .queryParam("center", googleStaticMap.centerLat() + "," + googleStaticMap.centerLng())
            .queryParam("size", googleStaticMap.sizeX() + "x" + googleStaticMap.sizeY());

        String hash = DigestUtils.md5DigestAsHex(mapsUrlBuilder.build().toUriString().getBytes());

        mapsUrlBuilder.queryParam("key", applicationProperties.getGoogleMapsKey());


        for (GoogleStaticMapMarker m : markers)
        {
            String markerValue = "";

            if (null != m.getSize())
            {
                markerValue += "size:" + m.getSize().toString().toLowerCase();
            }
            if (null != m.getColor())
            {
                markerValue += "%7Ccolor:" + m.getColor().toString().toLowerCase();
            }
            if (null != m.getLabel())
            {
                markerValue += "%7Clabel:" + m.getLabel();
            }
            markerValue += "%7C" + m.getCenterLat() + "," + m.getCenterLng();

            mapsUrlBuilder = mapsUrlBuilder.queryParam("markers", markerValue);
        }

        return new GoogleMapsUrlWithHash(mapsUrlBuilder.build().toUriString(), hash);
    }
}
