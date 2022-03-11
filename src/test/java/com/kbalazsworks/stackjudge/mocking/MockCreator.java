package com.kbalazsworks.stackjudge.mocking;

import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.common.services.PaginatorService;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressService;
import com.kbalazsworks.stackjudge.domain.common_module.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.PebbleTemplateFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.common_module.services.PebbleTemplateService;
import com.kbalazsworks.stackjudge.domain.common_module.services.UrlService;
import com.kbalazsworks.stackjudge.domain.company_module.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.company_module.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.email_module.services.CompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.domain.map_module.services.MapsService;
import com.kbalazsworks.stackjudge.domain.map_module.services.maps_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.notification_module.services.CrudNotificationService;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.services.PersistenceLogService;
import com.kbalazsworks.stackjudge.domain.review_module.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.domain.review_module.services.ReviewService;
import com.kbalazsworks.stackjudge.domain_aspects.services.SlowServiceLoggerAspectService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.aspectj.lang.ProceedingJoinPoint;

import static org.mockito.Mockito.mock;

public class MockCreator
{
    public static ApplicationProperties getApplicationPropertiesMock()
    {
        return mock(ApplicationProperties.class);
    }

    public static UrlFactory getUrlFactoryMock()
    {
        return mock(UrlFactory.class);
    }

    public static StaticProxyService getStaticProxyServiceMock()
    {
        return mock(StaticProxyService.class);
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

    public static PebbleTemplateService getPebbleTemplateService()
    {
        return mock(PebbleTemplateService.class);
    }

    public static SecureRandomService getSecureRandomService()
    {
        return mock(SecureRandomService.class);
    }

    public static UrlService getUrlService()
    {
        return mock(UrlService.class);
    }

    public static CompanyOwnEmailService getSendCompanyOwnEmailService()
    {
        return mock(CompanyOwnEmailService.class);
    }

    public static PersistenceLogService getPersistenceLogService()
    {
        return mock(PersistenceLogService.class);
    }

    public static PebbleTemplateFactory getPebbleTemplateFactoryMock()
    {
        return mock(PebbleTemplateFactory.class);
    }

    public static PebbleTemplate getPebbleTemplateMock()
    {
        return mock(PebbleTemplate.class);
    }

    public static SystemFactory getSystemFactoryMock()
    {
        return mock(SystemFactory.class);
    }

    public static ProceedingJoinPoint getProceedingJoinPointMock()
    {
        return mock(ProceedingJoinPoint.class);
    }

    public static SlowServiceLoggerAspectService getSlowServiceLoggerAspectService()
    {
        return mock(SlowServiceLoggerAspectService.class);
    }

    public static CrudNotificationService getCrudNotificationService()
    {
        return mock(CrudNotificationService.class);
    }
}
