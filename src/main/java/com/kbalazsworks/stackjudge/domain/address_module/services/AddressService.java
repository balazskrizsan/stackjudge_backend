package com.kbalazsworks.stackjudge.domain.address_module.services;

import com.kbalazsworks.stackjudge.domain_aspects.enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain_aspects.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.company_module.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.address_module.repositories.AddressRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService
{
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

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
                logger.error(
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
