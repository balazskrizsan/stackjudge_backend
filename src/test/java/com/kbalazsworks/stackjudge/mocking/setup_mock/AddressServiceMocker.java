package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressHttpException;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AddressServiceMocker extends MockCreator
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

    public static AddressService create_throws_AddressHttpException()
    {
        AddressService addressServiceMock = mock(AddressService.class);
        doThrow(AddressHttpException.class).when(addressServiceMock).create(Mockito.any());

        return addressServiceMock;
    }
}
