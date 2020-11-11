package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.session.entities.SessionState;
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
        addressRepository.create(address);
    }
}
