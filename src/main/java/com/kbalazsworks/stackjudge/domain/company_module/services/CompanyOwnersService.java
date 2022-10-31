package com.kbalazsworks.stackjudge.domain.company_module.services;

import com.kbalazsworks.stackjudge.domain_aspects.enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain_aspects.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwner;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.common_module.repositories.CompanyOwnersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyOwnersService
{
    private final CompanyOwnersRepository companyOwnersRepository;

    // @todo2: test
    public void own(@NonNull CompanyOwner companyOwner)
    {
        companyOwnersRepository.create(companyOwner);
    }

    // @todo2: test
    public boolean isUserOwnerOnCompany(String idsUserId, long companyId)
    {
        return companyOwnersRepository.isUserOwnerOnCompany(idsUserId, companyId);
    }

    // @todo2: test
    public Map<Long, List<String>> searchByCompanyId(List<Long> companyId)
    {
        return companyOwnersRepository.searchByCompanyId(companyId);
    }

    // @todo2: test
    @RedisCacheByCompanyIdList(repository = RedisCacheRepositorieEnum.COMPANY_OWNER)
    public Map<Long, CompanyOwners> searchWithCompanyIdGroupByCompany(List<Long> companyId)
    {
        return searchByCompanyId(companyId)
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, r -> new CompanyOwners(r.getKey(), r.getValue())));
    }
}
