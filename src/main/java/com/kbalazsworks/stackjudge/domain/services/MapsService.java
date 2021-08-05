package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.api.requests.maps_requests.MarkerRequest;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMaps;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.value_objects.service_responses.maps_service.StaticProxyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapsService
{
    private final CdnService cdnService;

    // @todo: test
    public StaticProxyResponse staticProxy(GoogleStaticMaps mapsRequest, List<MarkerRequest> markersRequests)
        throws ContentReadException
    {
        MarkerRequest marker = markersRequests.get(0);
        UriComponentsBuilder mapsUrlBuilder = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("maps.googleapis.com")
            .path("/maps/api/staticmap")
            .queryParam("maptype", mapsRequest.mapType())
            .queryParam("scale", mapsRequest.scale())
            .queryParam("zoom", mapsRequest.zoom())
            .queryParam("center", mapsRequest.centerLat() + "," + mapsRequest.centerLng())
            .queryParam("size", mapsRequest.sizeX() + "x" + mapsRequest.sizeY())
            .queryParam("key", "AIzaSyDBB7-jkE4MbAbC76s9abgdyk-UnD5gG6c");

        mapsUrlBuilder = mapsUrlBuilder.queryParam(
            "markers",
            "size:" + marker.getSize() + "%7Ccolor:red%7C" + marker.getCenterLat() + "," + marker.getCenterLng()
        );

        String mapsUrl = mapsUrlBuilder.build().toUriString();

        URL image;
        try
        {
            image = new URL(mapsUrl);
        }
        catch (MalformedURLException e)
        {
            throw new ContentReadException("Google maps content read error: " + e.getMessage());
        }
        String hash = DigestUtils.md5DigestAsHex(mapsUrl.getBytes());

        System.out.println(mapsUrl);

        var s3Response = cdnService.put(CdnNamespaceEnum.STATIC_MAPS, hash, "jpg", image);

        return new StaticProxyResponse(s3Response.path(), s3Response.fileName());
    }
}
