package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.services.maps.MapsService;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class MapsServiceMocker extends MockCreator
{
    public static MapsService searchByAddresses_returns_addressMaps(
        Map<Long, List<Address>> whenSearchAddresses,
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> thanAddressMaps
    )
    {
        MapsService mock = getMapsServiceMock();
        when(mock.searchByAddresses(whenSearchAddresses)).thenReturn(thanAddressMaps);

        return mock;
    }
}
