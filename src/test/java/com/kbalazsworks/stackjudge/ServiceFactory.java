package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.company_services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceFactory
{
    private final AddressService    addressService;
    private final SearchService     searchService;
    private final ReviewService     reviewService;
    private final PaginatorService  paginatorService;
    private final JooqService       jooqService;
    private final CdnService        cdnService;
    private final CompanyRepository companyRepository;

    public CompanyService getCompanyService()
    {
        return getCompanyService(null, null, null, null, null, null, null);
    }

    public CompanyService getCompanyService(
        AddressService addressServiceReplace,
        SearchService searchServiceReplace,
        ReviewService reviewServiceReplace,
        PaginatorService paginatorServiceReplace,
        JooqService jooqServiceReplace,
        CdnService cdnServiceReplace,
        CompanyRepository companyRepositoryReplace
    )
    {
        return new CompanyService(
            Optional.ofNullable(addressServiceReplace).orElse(addressService),
            Optional.ofNullable(searchServiceReplace).orElse(searchService),
            Optional.ofNullable(reviewServiceReplace).orElse(reviewService),
            Optional.ofNullable(paginatorServiceReplace).orElse(paginatorService),
            Optional.ofNullable(jooqServiceReplace).orElse(jooqService),
            Optional.ofNullable(cdnServiceReplace).orElse(cdnService),
            Optional.ofNullable(companyRepositoryReplace).orElse(companyRepository)
        );
    }
}
