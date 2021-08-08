package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.entities.google_static_maps.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.service_responses.maps_service.StaticProxyResponse;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MapsService
{
    private final CdnService                   cdnService;
    private final StaticProxyService           staticProxyService;
    private final GoogleStaticMapsCacheService googleStaticMapsCacheService;
    private final StateService                 stateService; // @todo: get it out

    // @todo: test
    public StaticProxyResponse staticProxy(
        GoogleStaticMap googleStaticMap,
        List<GoogleStaticMapMarker> markers,
        LocalDateTime now
    )
        throws ContentReadException
    {
        String mapsUrl = staticProxyService.generateMapUrl(googleStaticMap, markers);

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

    // @todo: test
    public Map<Long, Map<Long, String>> searchByAddresses(Map<Long, List<Address>> companyAddresses)
    {
        Map<Long, Map<Long, String>> mapsWithCompany = new HashMap<>();

        companyAddresses.forEach((companyId, addresses) -> {
            Map<Long, String> maps = new HashMap<>();

            addresses.forEach(address -> {
                StaticProxyResponse staticProxyResponse;
                try
                {
                    staticProxyResponse = staticProxy(
                        mapAddressToGoogleStaticMap(address),
                        new LinkedList<>() {{
                            add(mapAddressToGoogleStaticMapMarker(address));
                        }},
                        stateService.getState().now()
                    );
                }
                catch (ContentReadException e)
                {
                    // @todo: add log
                    return;
                }

                maps.put(address.id(), staticProxyResponse.location());
            });

            mapsWithCompany.put(companyId, maps);
        });

        return mapsWithCompany;
    }

    private GoogleStaticMapMarker mapAddressToGoogleStaticMapMarker(Address address)
    {
        return new GoogleStaticMapMarker(
            null,
            MarkerSizeEnum.BIG.getValue(),
            MarkerColorEnum.RED.getValue(),
            "",
            address.markerLat(),
            address.markerLng()
        );
    }

    private static GoogleStaticMap mapAddressToGoogleStaticMap(Address address)
    {
        return new GoogleStaticMap(
            null,
            660,
            220,
            (short) 1,
            (short) 16,
            (short) 1,
            address.markerLat(),
            address.markerLng()
        );
    }
}
