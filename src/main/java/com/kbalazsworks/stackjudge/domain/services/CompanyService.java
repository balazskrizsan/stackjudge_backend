package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.session.entities.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService
{
    private CompanyRepository companyRepository;
    private AddressService addressService;

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setAddressService(AddressService addressService)
    {
        this.addressService = addressService;
    }

    public void delete(long companyId)
    {
        companyRepository.delete(companyId);
    }

    public Company get(long companyId) throws RepositoryNotFoundException
    {
        return companyRepository.get(companyId);
    }

    public List<Company> search()
    {
        return companyRepository.search();
    }

    public void create(Company company, Address address)
    {
        Long newId = companyRepository.create(company);
        addressService.create(
            new Address(
                null,
                newId,
                address.fullAddress(),
                address.markerLat(),
                address.markerLng(),
                address.manualMarkerLat(),
                address.manualMarkerLng(),
                company.createdAt(),
                company.createdBy()
            )
        );
    }
}
