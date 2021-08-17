package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class AddressServiceMocks extends MockCreator
{
    public static AddressService search_returns_addressesMap(
        List<Long> whenCompaniesIds,
        Map<Long, List<Address>> thanForSearchAddresses
    )
    {
        AddressService addressServiceMock = getAddressServiceMock();
        when(addressServiceMock.search(whenCompaniesIds)).thenReturn(thanForSearchAddresses);

        return addressServiceMock;
    }
}
