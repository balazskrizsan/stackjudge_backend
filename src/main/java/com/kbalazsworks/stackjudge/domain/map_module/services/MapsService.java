package com.kbalazsworks.stackjudge.domain.map_module.services;

import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.aws_module.enums.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.common_module.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.map_module.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapSizeEnum;
import com.kbalazsworks.stackjudge.domain.map_module.services.maps_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleMapsUrlWithHash;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.OpenSdkFileService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.IV2S3Upload;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostUploadRequest;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PutAndSaveResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final StaticProxyService           staticProxyService;
    private final GoogleStaticMapsCacheService googleStaticMapsCacheService;
    private final MapMapperService             mapMapperService;
    private final OpenSdkFileService           openSdkFileService;
    private final IV2S3Upload                  v2S3UploadApiService;
    private final UrlFactory                   urlFactory;

    public StaticMapResponse staticProxy(GoogleStaticMap googleStaticMap, List<GoogleStaticMapMarker> markers)
    throws ContentReadException
    {
        return staticProxy(googleStaticMap, markers, MapPositionEnum.DEFAULT);
    }

    @SneakyThrows public StaticMapResponse staticProxy(
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

        StdResponse<PutAndSaveResponse> s3Response = v2S3UploadApiService
            .post(new PostUploadRequest(
                CdnNamespaceEnum.STATIC_MAPS.name(),
                "",
                hash,
                "jpg",
                openSdkFileService.createByteArrayResourceEntityFromString(
                    image.toString().getBytes(),
                    hash + ".jpg"
                )
            ));

        googleStaticMapsCacheService.create(new GoogleStaticMapsCache(
            hash,
            s3Response.data().getRemoteFile().getPath(),
            stateService.getState().now()
        ));

        return new StaticMapResponse(s3Response.data().getRemoteFile().getPath(), mapPositionEnum);
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

                maps.put(address.id(), mapsForAddress);
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
