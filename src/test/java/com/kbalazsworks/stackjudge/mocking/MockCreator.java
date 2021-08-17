package com.kbalazsworks.stackjudge.mocking;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.state.services.AccountService;

import static org.mockito.Mockito.mock;

public class MockCreator
{
    public static CdnService getCdnServiceMock()
    {
        return mock(CdnService.class);
    }

    public static UrlFactory getUrlFactoryMock()
    {
        return mock(UrlFactory.class);
    }

    public static StaticProxyService getStaticProxyServiceMock()
    {
        return mock(StaticProxyService.class);
    }

    public static PutObjectResult getPutObjectResultMock()
    {
        return mock(PutObjectResult.class);
    }

    public static CompanyRepository getCompanyRepositoryMock()
    {
        return mock(CompanyRepository.class);
    }

    public static SearchService getSearchServiceMock()
    {
        return mock(SearchService.class);
    }

    public static ReviewService getReviewServiceMock()
    {
        return mock(ReviewService.class);
    }

    public static AddressService getAddressServiceMock()
    {
        return mock(AddressService.class);
    }

    public static PaginatorService getPaginatorServiceMock()
    {
        return mock(PaginatorService.class);
    }

    public static MapsService getMapsServiceMock()
    {
        return mock(MapsService.class);
    }

    public static AccountService getAccountServiceMock()
    {
        return mock(AccountService.class);
    }
}
