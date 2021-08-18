package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressHttpException;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AddressServiceMocker extends MockCreator
{
    public static AddressService search_returns_addressesMap(
        List<Long> whenCompaniesIds,
        Map<Long, List<Address>> thanForSearchAddresses
    )
    {
        AddressService mock = getAddressServiceMock();
        when(mock.search(whenCompaniesIds)).thenReturn(thanForSearchAddresses);

        return mock;
    }

    public static AddressService create_throws_AddressHttpException()
    {
        AddressService mock = getAddressServiceMock();
        doThrow(AddressHttpException.class).when(mock).create(Mockito.any());

        return mock;
    }
}
