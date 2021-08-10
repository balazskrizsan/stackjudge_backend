package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.entities.google_static_maps.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapSizeEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.service_responses.maps_service.StaticMapResponse;
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
    public StaticMapResponse staticProxy(
        GoogleStaticMap googleStaticMap,
        List<GoogleStaticMapMarker> markers,
        LocalDateTime now
    ) throws ContentReadException
    {
        return staticProxy(googleStaticMap, markers, MapPositionEnum.DEFAULT, now);
    }

    // @todo: test
    public StaticMapResponse staticProxy(
        GoogleStaticMap googleStaticMap,
        List<GoogleStaticMapMarker> markers,
        MapPositionEnum mapPositionEnum,
        LocalDateTime now
    ) throws ContentReadException
    {
        String mapsUrl = staticProxyService.generateMapUrl(googleStaticMap, markers);
        String hash    = DigestUtils.md5DigestAsHex(mapsUrl.getBytes());

        try
        {
            GoogleStaticMapsCache googleStaticMapsCache = googleStaticMapsCacheService.getByHash(hash);

            return new StaticMapResponse(googleStaticMapsCache.fileName(), mapPositionEnum);
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

        return new StaticMapResponse(s3Response.path(), mapPositionEnum);
    }

    // @todo: test
    public Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> searchByAddresses(Map<Long, List<Address>> companyAddresses)
    {
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> mapsWithCompany = new HashMap<>();

        final boolean[] isFirst = {true};
        companyAddresses.forEach((companyId, addresses) -> {
            Map<Long, Map<MapPositionEnum, StaticMapResponse>> maps = new HashMap<>();

            addresses.forEach(address -> {
                Map<MapPositionEnum, StaticMapResponse> mapsForAddress = new HashMap<>();
                if (isFirst[0])
                {
                    mapsForAddress.put(
                        MapPositionEnum.COMPANY_HEADER,
                        getMap(address, MapSizeEnum.COMPANY_HEADER, MapPositionEnum.COMPANY_HEADER)
                    );
                    isFirst[0] = false;
                }

                mapsForAddress.put(
                    MapPositionEnum.COMPANY_LEFT,
                    getMap(address, MapSizeEnum.COMPANY_LEFT, MapPositionEnum.COMPANY_LEFT)
                );
                maps.put(address.id(), mapsForAddress);
            });

            mapsWithCompany.put(companyId, maps);
        });

        return mapsWithCompany;
    }

    private StaticMapResponse getMap(Address address, MapSizeEnum mapSizeEnum, MapPositionEnum mapPositionEnum)
    {
        try
        {
            return staticProxy(
                mapAddressToGoogleStaticMap(address, mapSizeEnum),
                new LinkedList<>()
                {{
                    add(mapAddressToGoogleStaticMapMarker(address));
                }},
                mapPositionEnum,
                stateService.getState().now()
            );
        }
        catch (ContentReadException e)
        {
            // @todo: add log
            return null;
        }
    }

    private GoogleStaticMapMarker mapAddressToGoogleStaticMapMarker(Address address)
    {
        return new GoogleStaticMapMarker(
            MarkerSizeEnum.BIG,
            MarkerColorEnum.RED,
            "",
            address.markerLat(),
            address.markerLng()
        );
    }

    private static GoogleStaticMap mapAddressToGoogleStaticMap(Address address, MapSizeEnum mapSizeEnum)
    {
        return new GoogleStaticMap(
            mapSizeEnum.getX(),
            mapSizeEnum.getY(),
            mapSizeEnum.getScale(),
            mapSizeEnum.getZoom(),
            mapSizeEnum.getMapType(),
            address.markerLat(),
            address.markerLng()
        );
    }
}
