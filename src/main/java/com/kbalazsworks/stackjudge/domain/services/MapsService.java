package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMaps;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.entities.google_static_maps.GoogleMapsMarker;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.service_responses.maps_service.StaticProxyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapsService
{
    private final CdnService                   cdnService;
    private final StaticProxyService           staticProxyService;
    private final GoogleStaticMapsCacheService googleStaticMapsCacheService;

    // @todo: test
    public StaticProxyResponse staticProxy(
        GoogleStaticMaps googleStaticMaps,
        List<GoogleMapsMarker> markers,
        LocalDateTime now
    )
        throws ContentReadException
    {
        String mapsUrl = staticProxyService.generateMapUrl(googleStaticMaps, markers);

        String hash = DigestUtils.md5DigestAsHex(mapsUrl.getBytes());

        try
        {
            GoogleStaticMapsCache googleStaticMapsCache = googleStaticMapsCacheService.getByHash(hash);

            return new StaticProxyResponse(googleStaticMapsCache.fileName());
        }
        catch (RepositoryNotFoundException ignored)
        {
        }

        URL image;
        try
        {
            image = new URL(mapsUrl);
        }
        catch (MalformedURLException e)
        {
            throw new ContentReadException("Google maps content read error: " + e.getMessage());
        }

        CdnServicePutResponse s3Response = cdnService.put(CdnNamespaceEnum.STATIC_MAPS, hash, "jpg", image);

        googleStaticMapsCacheService.create(new GoogleStaticMapsCache(hash, s3Response.path(), now));

        return new StaticProxyResponse(s3Response.path());
    }
}
