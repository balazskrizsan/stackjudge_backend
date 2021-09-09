package com.kbalazsworks.stackjudge.integration.domain.services.maps.maps_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.services.maps.MapMapperService;
import com.kbalazsworks.stackjudge.domain.services.maps.MapsService;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapMarkerFakeBuilder;
import com.kbalazsworks.stackjudge.helpers.ObjectComparatorService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.kbalazsworks.stackjudge.domain.enums.google_maps.MapSizeEnum.COMPANY_HEADER;
import static com.kbalazsworks.stackjudge.domain.enums.google_maps.MapSizeEnum.COMPANY_LEFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MapsService_CompanyAddresses extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private static boolean compareGoogleStaticMaps(GoogleStaticMap g)
    {
        if (null == g)
        {
            return false;
        }

        return new ObjectComparatorService().byValues(g, new GoogleStaticMapFakeBuilder().build());
    }

    @Test
    @SneakyThrows
    public void validAddresses_returnsCachedMapStructure()
    {
        // Arrange
        Map<Long, List<Address>> testedAddresses = Map.of(
            CompanyFakeBuilder.defaultId1,
            List.of(new AddressFakeBuilder().build())
        );
        StaticMapResponse mockStaticProxyResponse1 = new StaticMapResponse(
            "test/location1",
            MapPositionEnum.COMPANY_HEADER
        );
        StaticMapResponse mockStaticProxyResponse2 = new StaticMapResponse(
            "test/location2",
            MapPositionEnum.COMPANY_LEFT
        );

        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> expectedResponse = Map.of(
            CompanyFakeBuilder.defaultId1, Map.of(AddressFakeBuilder.defaultId1, Map.of(
                    MapPositionEnum.COMPANY_LEFT, new StaticMapResponse("test/location1", MapPositionEnum.COMPANY_HEADER),
                    MapPositionEnum.COMPANY_HEADER, new StaticMapResponse("test/location2", MapPositionEnum.COMPANY_LEFT)
                )
            )
        );

        MapMapperService mapMapperService = mock(MapMapperService.class);
        when(mapMapperService.mapAddressToGoogleStaticMap(new AddressFakeBuilder().build(), COMPANY_HEADER))
            .thenReturn(new GoogleStaticMapFakeBuilder().build());
        when(mapMapperService.mapAddressToGoogleStaticMap(new AddressFakeBuilder().build(), COMPANY_LEFT))
            .thenReturn(new GoogleStaticMapFakeBuilder().build());
        when(mapMapperService.mapAddressToGoogleStaticMapMarker(new AddressFakeBuilder().build()))
            .thenReturn(new GoogleStaticMapMarkerFakeBuilder().build());

        MapsService mapsServicePartialMock = spy(serviceFactory.getMapsService(
            null,
            null,
            null,
            null,
            mapMapperService,
            null
        ));
        doReturn(mockStaticProxyResponse1)
            .when(mapsServicePartialMock)
            .staticProxy(
                argThat(MapsService_CompanyAddresses::compareGoogleStaticMaps),
                any(), // @todo: add check
                eq(MapPositionEnum.COMPANY_LEFT)
            );
        doReturn(mockStaticProxyResponse2)
            .when(mapsServicePartialMock)
            .staticProxy(
                argThat(MapsService_CompanyAddresses::compareGoogleStaticMaps),
                any(), // @todo: add check
                eq(MapPositionEnum.COMPANY_HEADER)
            );

        // Act
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> actual
            = mapsServicePartialMock.searchByAddresses(testedAddresses);

        // Assert
        assertThat(actual).isEqualTo(expectedResponse);
    }
}
