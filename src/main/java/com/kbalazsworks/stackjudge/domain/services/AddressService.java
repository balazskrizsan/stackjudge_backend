package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressException;
import com.kbalazsworks.stackjudge.domain.repositories.AddressRepository;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService
{
    private AddressRepository addressRepository;

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

    public void create(Address address)
    {
        try
        {
            addressRepository.create(address);
        }
        catch (DataAccessException e)
        {
            if (e.getCause().toString().contains("fk__address_company_id__company_id__on_delete_cascade"))
            {
                throw new AddressException("Missing company; id#".concat(address.companyId().toString()));
            }

            throw e;
        }
    }
}
