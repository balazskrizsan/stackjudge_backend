package com.kbalazsworks.stackjudge.domain.address_module.services;

import com.kbalazsworks.stackjudge.domain.address_module.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.company_module.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain_aspects.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain_aspects.enums.RedisCacheRepositorieEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.exception.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService
{
    private final AddressRepository addressRepository;

    public void create(@NonNull Address address)
    {
        try
        {
            addressRepository.create(address);
        }
        catch (DataAccessException e)
        {
            if (e.getCause().toString().contains("fk__address_company_id__company_id__on_delete_cascade"))
            {
                log.error(
                    ExceptionResponseInfo.CompanyNotFoundMsg
                        .concat(" id%")
                        .concat(address.companyId().toString())
                );

                throw new CompanyHttpException(ExceptionResponseInfo.CompanyNotFoundMsg)
                    .withErrorCode(ExceptionResponseInfo.CompanyNotFoundErrorCode)
                    .withStatusCode(HttpStatus.BAD_REQUEST);
            }

            throw e;
        }
    }

    public Map<Long, List<Address>> search(List<Long> companyIds)
    {
        return addressRepository.search(companyIds);
    }

    @RedisCacheByCompanyIdList(repository = RedisCacheRepositorieEnum.ADDRESS)
    public Map<Long, CompanyAddresses> searchWithCompanyAddresses(List<Long> companyIds)
    {
        return addressRepository
            .search(companyIds)
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, r -> new CompanyAddresses(r.getKey(), r.getValue())));
    }
}
