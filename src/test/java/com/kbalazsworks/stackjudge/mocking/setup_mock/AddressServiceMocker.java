package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.address_module.exceptions.AddressHttpException;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressService;
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

    public static AddressService searchWithCompanyAddresses_returns_addressesMap(
        List<Long> whenCompaniesIds,
        Map<Long, CompanyAddresses> thanForSearchAddresses
    )
    {
        AddressService mock = getAddressServiceMock();
        when(mock.searchWithCompanyAddresses(whenCompaniesIds)).thenReturn(thanForSearchAddresses);

        return mock;
    }

    public static AddressService create_throws_AddressHttpException()
    {
        AddressService mock = getAddressServiceMock();
        doThrow(AddressHttpException.class).when(mock).create(Mockito.any());

        return mock;
    }
}
