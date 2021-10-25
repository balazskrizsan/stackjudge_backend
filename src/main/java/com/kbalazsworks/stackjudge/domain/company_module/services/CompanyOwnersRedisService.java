package com.kbalazsworks.stackjudge.domain.company_module.services;

import com.google.common.collect.ImmutableList;
import com.kbalazsworks.stackjudge.domain.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.company_module.repositories.CompanyOwnerRedisRepository;
import com.kbalazsworks.stackjudge.domain.common_module.services.IRedisService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyOwnersRedisService implements IRedisService<CompanyOwners>
{
    private final CompanyOwnerRedisRepository companyOwnerRedisRepository;

    @Override
    public List<CompanyOwners> findAllById(@NonNull List<Long> ids)
    {
        List<String> stringIds = ids.stream().map(Object::toString).collect(Collectors.toList());

        return ImmutableList.copyOf(companyOwnerRedisRepository.findAllById(stringIds));
    }

    @Override
    public void saveAll(@NonNull List<CompanyOwners> entities)
    {
        companyOwnerRedisRepository.saveAll(entities);
    }
}
