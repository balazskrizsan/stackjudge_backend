package com.kbalazsworks.stackjudge.mocking;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.domain.factories.AmazonS3ClientFactory;
import com.kbalazsworks.stackjudge.domain.factories.AmazonSimpleEmailServiceFactory;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.aws_services.SesService;
import com.kbalazsworks.stackjudge.domain.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.services.AccountService;

import static org.mockito.Mockito.mock;

public class MockCreator
{
    public static ApplicationProperties getApplicationPropertiesMock()
    {
        return mock(ApplicationProperties.class);
    }

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

    public static JwtSubService getJwtSubServiceMock()
    {
        return mock(JwtSubService.class);
    }

    public static AmazonS3ClientFactory getAmazonS3ClientFactoryMock()
    {
        return mock(AmazonS3ClientFactory.class);
    }

    public static AmazonS3 getAmazonS3Mock()
    {
        return mock(AmazonS3.class);
    }

    public static GroupService getGroupServiceMock()
    {
        return mock(GroupService.class);
    }

    public static DateFactory getDateFactoryMock()
    {
        return mock(DateFactory.class);
    }

    public static ProtectedReviewLogService getProtectedReviewLogServiceMock()
    {
        return mock(ProtectedReviewLogService.class);
    }

    public static AmazonSimpleEmailServiceFactory getAmazonSimpleEmailServiceFactory()
    {
        return mock(AmazonSimpleEmailServiceFactory.class);
    }

    public static AmazonSimpleEmailService getAmazonSimpleEmailService()
    {
        return mock(AmazonSimpleEmailService.class);
    }

    public static PebbleTemplateService getPebbleTemplateService()
    {
        return mock(PebbleTemplateService.class);
    }

    public static SesService getSesService()
    {
        return mock(SesService.class);
    }
}
