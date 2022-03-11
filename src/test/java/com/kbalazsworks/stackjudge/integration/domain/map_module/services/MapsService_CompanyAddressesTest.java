package com.kbalazsworks.stackjudge.integration.domain.map_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.services.MapMapperService;
import com.kbalazsworks.stackjudge.domain.map_module.services.MapsService;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
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

import static com.kbalazsworks.stackjudge.domain.map_module.enums.MapSizeEnum.COMPANY_HEADER;
import static com.kbalazsworks.stackjudge.domain.map_module.enums.MapSizeEnum.COMPANY_LEFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MapsService_CompanyAddressesTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private static final ObjectComparatorService objectComparatorService =  new ObjectComparatorService();

    private static boolean compareGoogleStaticMaps(GoogleStaticMap g)
    {
        if (null == g)
        {
            return false;
        }

        return argThatObjectStatic(g, new GoogleStaticMapFakeBuilder().build());
    }

    private static boolean compareGoogleStaticMapMarker(List<GoogleStaticMapMarker> g)
    {
        if (null == g || g.isEmpty())
        {
            return false;
        }

        return argThatObjectStatic(g.get(0), new GoogleStaticMapMarkerFakeBuilder().build());
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
            mapMapperService,
            null
        ));
        doReturn(new StaticMapResponse("test/location1", MapPositionEnum.COMPANY_HEADER))
            .when(mapsServicePartialMock)
            .staticProxy(
                argThat(MapsService_CompanyAddressesTest::compareGoogleStaticMaps),
                argThat(MapsService_CompanyAddressesTest::compareGoogleStaticMapMarker),
                eq(MapPositionEnum.COMPANY_LEFT)
            );
        doReturn(new StaticMapResponse("test/location2", MapPositionEnum.COMPANY_LEFT))
            .when(mapsServicePartialMock)
            .staticProxy(
                argThat(MapsService_CompanyAddressesTest::compareGoogleStaticMaps),
                argThat(MapsService_CompanyAddressesTest::compareGoogleStaticMapMarker),
                eq(MapPositionEnum.COMPANY_HEADER)
            );

        // Act
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> actual
            = mapsServicePartialMock.searchByAddresses(testedAddresses);

        // Assert
        assertThat(actual).isEqualTo(expectedResponse);
    }
}
