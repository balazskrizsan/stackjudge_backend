package com.kbalazsworks.stackjudge.domain.services.company;

import com.kbalazsworks.stackjudge.domain.entities.CompanyOwner;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyOwnersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public boolean isUserOwnerOnCompany(long userId, long companyId)
    {
        return companyOwnersRepository.isUserOwnerOnCompany(userId, companyId);
    }
}
