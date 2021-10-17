package com.kbalazsworks.stackjudge.domain.services.company;

import com.kbalazsworks.stackjudge.domain.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.redis_repositories.CompanyOwnerRedisRepository;
import com.kbalazsworks.stackjudge.domain.services.IRedisService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyOwnersRedisService implements IRedisService<CompanyOwners>
{
    private final CompanyOwnerRedisRepository companyOwnerRedisRepository;

    @Override
    public Iterable<CompanyOwners> findAllById(@NonNull Iterable<String> ids)
    {
        return companyOwnerRedisRepository.findAllById(ids);
    }

    @Override
    public void saveAll(@NonNull List<CompanyOwners> entities)
    {
        companyOwnerRedisRepository.saveAll(entities);
    }
}
