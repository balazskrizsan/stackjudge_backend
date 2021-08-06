package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMaps;
import com.kbalazsworks.stackjudge.domain.entities.google_static_maps.GoogleMapsMarker;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.service_responses.maps_service.StaticProxyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapsService
{
    private final CdnService         cdnService;
    private final StaticProxyService staticProxyService;

    // @todo: test
    public StaticProxyResponse staticProxy(GoogleStaticMaps googleStaticMaps, List<GoogleMapsMarker> markers)
        throws ContentReadException
    {
        String mapsUrl = staticProxyService.generateMapUrl(googleStaticMaps, markers);

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

        CdnServicePutResponse s3Response = cdnService.put(CdnNamespaceEnum.STATIC_MAPS, hash, "jpg", image);

        return new StaticProxyResponse(s3Response.path(), s3Response.fileName());
    }
}
