package com.kbalazsworks.stackjudge.domain.services.maps;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapSizeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.services.CdnService;
import com.kbalazsworks.stackjudge.domain.services.GoogleStaticMapsCacheService;
import com.kbalazsworks.stackjudge.domain.services.maps.marp_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleMapsUrlWithHash;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MapsService
{
    private final StateService                 stateService;
    private final CdnService                   cdnService;
    private final StaticProxyService           staticProxyService;
    private final GoogleStaticMapsCacheService googleStaticMapsCacheService;
    private final MapMapperService             mapMapperService;
    private final UrlFactory                   urlFactory;

    public StaticMapResponse staticProxy(GoogleStaticMap googleStaticMap, List<GoogleStaticMapMarker> markers)
        throws ContentReadException
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

    public Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> searchByAddresses(
        Map<Long, List<Address>> companyAddresses
    )
    {
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> mapsWithCompany = new HashMap<>();

        final boolean[] isFirst = {true};
        companyAddresses.forEach((companyId, addresses) -> {
            Map<Long, Map<MapPositionEnum, StaticMapResponse>> maps = new HashMap<>();

            addresses.forEach(address -> {
                Map<MapPositionEnum, StaticMapResponse> mapsForAddress = new HashMap<>();
                if (isFirst[0])
                {
                    StaticMapResponse headerMap = getMap(
                        address,
                        MapSizeEnum.COMPANY_HEADER,
                        MapPositionEnum.COMPANY_HEADER
                    );
                    if (null != headerMap)
                    {
                        mapsForAddress.put(MapPositionEnum.COMPANY_HEADER, headerMap);
                    }
                    isFirst[0] = false;
                }

                StaticMapResponse leftMap = getMap(address, MapSizeEnum.COMPANY_LEFT, MapPositionEnum.COMPANY_LEFT);
                if (null != leftMap)
                {
                    mapsForAddress.put(MapPositionEnum.COMPANY_LEFT, leftMap);
                }

                maps.put(address.redisCacheId(), mapsForAddress);
            });

            mapsWithCompany.put(companyId, maps);
        });

        return mapsWithCompany;
    }

    private @Nullable StaticMapResponse getMap(
        @NonNull Address address,
        @NotNull MapSizeEnum mapSizeEnum,
        @NonNull MapPositionEnum mapPositionEnum
    )
    {
        try
        {
            return staticProxy(
                mapMapperService.mapAddressToGoogleStaticMap(address, mapSizeEnum),
                List.of(mapMapperService.mapAddressToGoogleStaticMapMarker(address)),
                mapPositionEnum
            );
        }
        catch (ContentReadException e)
        {
            log.error("Google map error: " + e.getMessage());

            return null;
        }
    }
}
