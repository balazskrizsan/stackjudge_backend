package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapSizeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.services.map_service.MapMapperService;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleMapsUrlWithHash;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MapsService
{
    private final StateService                 stateService;
    private final CdnService                   cdnService;
    private final StaticProxyService           staticProxyService;
    private final GoogleStaticMapsCacheService googleStaticMapsCacheService;
    private final MapMapperService             mapMapperService;
    private final UrlFactory                   urlFactory;

    // @todo: test
    public StaticMapResponse staticProxy(
        GoogleStaticMap googleStaticMap,
        List<GoogleStaticMapMarker> markers
    ) throws ContentReadException
    {
        return staticProxy(googleStaticMap, markers, MapPositionEnum.DEFAULT);
    }

    public StaticMapResponse staticProxy(
        GoogleStaticMap googleStaticMap,
        List<GoogleStaticMapMarker> markers,
        MapPositionEnum mapPositionEnum
    ) throws ContentReadException
    {
        GoogleMapsUrlWithHash mapWithHash = staticProxyService.generateMapUrl(googleStaticMap, markers);
        String                hash        = mapWithHash.hash();

        try
        {
            GoogleStaticMapsCache googleStaticMapsCache = googleStaticMapsCacheService.getByHash(hash);

            return new StaticMapResponse(googleStaticMapsCache.fileName(), mapPositionEnum);
        }
        catch (RepositoryNotFoundException ignored)
        {
        }

        URL image = urlFactory.create(mapWithHash.url());

        CdnServicePutResponse s3Response = cdnService.put(CdnNamespaceEnum.STATIC_MAPS, hash, "jpg", image);

        googleStaticMapsCacheService.create(new GoogleStaticMapsCache(
            hash,
            s3Response.path(),
            stateService.getState().now()
        ));

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
                mapMapperService.mapAddressToGoogleStaticMap(address, mapSizeEnum),
                new LinkedList<>()
                {{
                    add(mapMapperService.mapAddressToGoogleStaticMapMarker(address));
                }},
                mapPositionEnum
            );
        }
        catch (ContentReadException e)
        {
            // @todo: add log
            return null;
        }
    }
}
