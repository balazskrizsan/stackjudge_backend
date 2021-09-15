package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.factories.AmazonSimpleEmailServiceFactory;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.factories.PebbleTemplateFactory;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyOwnRequestRepository;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.repositories.GroupRepository;
import com.kbalazsworks.stackjudge.domain.repositories.ReviewRepository;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.domain.services.CdnService;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.services.DateTimeFormatterService;
import com.kbalazsworks.stackjudge.domain.services.GoogleStaticMapsCacheService;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.domain.services.HttpExceptionService;
import com.kbalazsworks.stackjudge.domain.services.JooqService;
import com.kbalazsworks.stackjudge.domain.services.company.CompanyOwnersService;
import com.kbalazsworks.stackjudge.domain.services.maps.MapsService;
import com.kbalazsworks.stackjudge.domain.services.PaginatorService;
import com.kbalazsworks.stackjudge.domain.services.PebbleTemplateService;
import com.kbalazsworks.stackjudge.domain.services.PersistenceLogService;
import com.kbalazsworks.stackjudge.domain.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.domain.services.UrlService;
import com.kbalazsworks.stackjudge.domain.services.aws_services.SendCompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.services.aws_services.SesService;
import com.kbalazsworks.stackjudge.domain.services.company.OwnRequestService;
import com.kbalazsworks.stackjudge.domain.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.services.maps.MapMapperService;
import com.kbalazsworks.stackjudge.domain.services.maps.marp_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.services.notification_service.SearchMyNotificationsService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.ApplicationPropertiesMocker;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Service
@RequiredArgsConstructor
public class ServiceFactory
{
    private final ApplicationProperties           applicationProperties;
    private final DateFactory                     dateFactory;
    private final SystemFactory                   systemFactory;
    private final JwtFactory                      jwtFactory;
    private final LocalDateTimeFactory            localDateTimeFactory;
    private final UrlFactory                      urlFactory;
    private final AmazonSimpleEmailServiceFactory amazonSimpleEmailServiceFactory;
    private final PebbleTemplateFactory           pebbleTemplateFactory;

    private final AddressService               addressService;
    private final SearchService                searchService;
    private final GroupService                 groupService;
    private final ReviewService                reviewService;
    private final PaginatorService             paginatorService;
    private final JwtSubService                jwtSubService;
    private final DateTimeFormatterService     dateTimeFormatterService;
    private final JooqService                  jooqService;
    private final CdnService                   cdnService;
    private final AccountService               accountService;
    private final MapsService                  mapsService;
    private final StaticProxyService           staticProxyService;
    private final GoogleStaticMapsCacheService googleStaticMapsCacheService;
    private final MapMapperService             mapMapperService;
    private final ProtectedReviewLogService    protectedReviewLogService;
    private final SesService                   sesService;
    private final PebbleTemplateService        pebbleTemplateService;
    private final PersistenceLogService        persistenceLogService;
    private final SecureRandomService          secureRandomService;
    private final SendCompanyOwnEmailService   sendCompanyOwnEmailService;
    private final UrlService                   urlService;
    private final CompanyService               companyService;
    private final HttpExceptionService         httpExceptionService;
    private final CompanyOwnersService         companyOwnersService;

    private final CompanyRepository           companyRepository;
    private final ReviewRepository            reviewRepository;
    private final GroupRepository             groupRepository;
    private final S3Repository                s3Repository;
    private final AddressRepository           addressRepository;
    private final UsersRepository             usersRepository;
    private final CompanyOwnRequestRepository companyOwnRequestRepository;

    public CompanyService getCompanyService()
    {
        return getCompanyService(null, null, null, null, null, null, null, null, null);
    }

    public CompanyService getCompanyService(
        AddressService addressServiceReplacer,
        SearchService searchServiceReplacer,
        ReviewService reviewServiceReplacer,
        PaginatorService paginatorServiceReplacer,
        JooqService jooqServiceReplacer,
        CdnService cdnServiceReplacer,
        AccountService accountServiceReplaces,
        MapsService mapsServiceReplacer,
        CompanyRepository companyRepositoryReplacer
    )
    {
        return new CompanyService(
            Optional.ofNullable(addressServiceReplacer).orElse(addressService),
            Optional.ofNullable(searchServiceReplacer).orElse(searchService),
            Optional.ofNullable(reviewServiceReplacer).orElse(reviewService),
            Optional.ofNullable(paginatorServiceReplacer).orElse(paginatorService),
            Optional.ofNullable(jooqServiceReplacer).orElse(jooqService),
            Optional.ofNullable(cdnServiceReplacer).orElse(cdnService),
            Optional.ofNullable(accountServiceReplaces).orElse(accountService),
            Optional.ofNullable(mapsServiceReplacer).orElse(mapsService),
            Optional.ofNullable(companyRepositoryReplacer).orElse(companyRepository)
        );
    }

    public JwtService getJwtService()
    {
        return getJwtService(null, null, null, null);
    }

    public JwtService getJwtService(
        ApplicationProperties applicationPropertiesReplacer,
        DateFactory dateFactoryReplacer,
        SystemFactory systemFactoryReplacer,
        JwtSubService jwtSubServiceReplacer
    )
    {
        return new JwtService(
            Optional.ofNullable(applicationPropertiesReplacer).orElse(ApplicationPropertiesMocker.getDefaultMock()),
            Optional.ofNullable(dateFactoryReplacer).orElse(dateFactory),
            Optional.ofNullable(systemFactoryReplacer).orElse(systemFactory),
            Optional.ofNullable(jwtSubServiceReplacer).orElse(jwtSubService)
        );
    }

    public JwtService getJwtMockedService(
        ApplicationProperties applicationPropertiesMock,
        DateFactory dateFactoryMock,
        SystemFactory systemFactoryMock,
        JwtSubService jwtSubServiceMock
    )
    {
        if (null == applicationPropertiesMock)
        {
            applicationPropertiesMock = ApplicationPropertiesMocker.getDefaultMock();
        }

        if (null == dateFactoryMock)
        {
            String testedTime            = "2021-01-02 11:22:33";
            String testedTimePlusOneWeek = "2021-01-09 11:22:33";
            dateFactoryMock = MockCreator.getDateFactoryMock();
            when(dateFactoryMock.create()).thenReturn(MockFactory.getJavaDateFromDateTime(testedTime));
            when(dateFactoryMock.create(anyLong()))
                .thenReturn(MockFactory.getJavaDateFromDateTime(testedTimePlusOneWeek));
        }

        return getJwtService(applicationPropertiesMock, dateFactoryMock, systemFactoryMock, jwtSubServiceMock);
    }

    public JwtSubService getJwtSubService()
    {
        return getJwtSubService(null, null);
    }

    public JwtSubService getJwtSubService(
        ApplicationProperties applicationPropertiesReplacer,
        JwtFactory jwtFactoryReplacer
    )
    {
        return new JwtSubService(
            Optional.ofNullable(applicationPropertiesReplacer).orElse(applicationProperties),
            Optional.ofNullable(jwtFactoryReplacer).orElse(jwtFactory)
        );
    }

    public ReviewService getReviewService()
    {
        return getReviewService(null);
    }

    public ReviewService getReviewService(ReviewRepository reviewRepositoryReplacer)
    {
        return new ReviewService(Optional.ofNullable(reviewRepositoryReplacer).orElse(reviewRepository));
    }

    public GroupService getGroupService()
    {
        return getGroupService(null);
    }

    public GroupService getGroupService(GroupRepository groupRepositoryReplacer)
    {
        return new GroupService(Optional.ofNullable(groupRepositoryReplacer).orElse(groupRepositoryReplacer));
    }

    public SearchService getSearchService()
    {
        return getSearchService(null);
    }

    public SearchService getSearchService(GroupService groupServiceReplacer)
    {
        return new SearchService(Optional.ofNullable(groupServiceReplacer).orElse(groupService));
    }

    public CdnService getCdnService()
    {
        return getCdnService(null, null, null, null);
    }

    public CdnService getCdnService(
        ApplicationProperties applicationPropertiesReplacer,
        LocalDateTimeFactory localDateTimeFactoryReplacer,
        DateTimeFormatterService dateTimeFormatterServiceReplacer,
        S3Repository s3RepositoryReplacer
    )
    {
        return new CdnService(
            Optional.ofNullable(applicationPropertiesReplacer).orElse(applicationProperties),
            Optional.ofNullable(localDateTimeFactoryReplacer).orElse(localDateTimeFactory),
            Optional.ofNullable(dateTimeFormatterServiceReplacer).orElse(dateTimeFormatterService),
            Optional.ofNullable(s3RepositoryReplacer).orElse(s3Repository)
        );
    }

    public StaticProxyService getStaticProxyService()
    {
        return getStaticProxyService(null);
    }

    public StaticProxyService getStaticProxyService(ApplicationProperties applicationPropertiesReplacer)
    {
        return new StaticProxyService(
            Optional.ofNullable(applicationPropertiesReplacer).orElse(applicationProperties)
        );
    }

    public MapsService getMapsService()
    {
        return getMapsService(null, null, null, null, null, null);
    }

    public MapsService getMapsService(
        StateService stateServiceReplacer,
        CdnService cdnServiceReplacer,
        StaticProxyService staticProxyServiceReplacer,
        GoogleStaticMapsCacheService staticMapsCacheServiceReplacer,
        MapMapperService mapMapperServiceReplacer,
        UrlFactory urlFactoryReplacer
    )
    {
        return new MapsService(
            Optional.ofNullable(stateServiceReplacer).orElse(MockFactory.getTestStateMock()),
            Optional.ofNullable(cdnServiceReplacer).orElse(cdnService),
            Optional.ofNullable(staticProxyServiceReplacer).orElse(staticProxyService),
            Optional.ofNullable(staticMapsCacheServiceReplacer).orElse(googleStaticMapsCacheService),
            Optional.ofNullable(mapMapperServiceReplacer).orElse(mapMapperService),
            Optional.ofNullable(urlFactoryReplacer).orElse(urlFactory)
        );
    }

    public SearchMyNotificationsService getSearchMyNotificationsService()
    {
        return new SearchMyNotificationsService();
    }

    public SecureRandomService getSecureRandomService()
    {
        return new SecureRandomService();
    }

    public AddressService getAddressService()
    {
        return new AddressService(addressRepository);
    }

    public PaginatorService getPaginatorService()
    {
        return new PaginatorService();
    }

    public AccountService getAccountService()
    {
        return getAccountService(null, null);
    }

    public AccountService getAccountService(
        UsersRepository usersRepositoryReplacer,
        ProtectedReviewLogService protectedReviewLogServiceReplacer
    )
    {
        return new AccountService(
            Optional.ofNullable(usersRepositoryReplacer).orElse(usersRepository),
            Optional.ofNullable(protectedReviewLogServiceReplacer).orElse(protectedReviewLogService)
        );
    }

    public HttpExceptionService getHttpExceptionService()
    {
        return new HttpExceptionService();
    }

    public SesService getSesService()
    {
        return getSesService(null);
    }

    public SesService getSesService(AmazonSimpleEmailServiceFactory amazonSimpleEmailServiceFactoryReplacer)
    {
        return new SesService(
            Optional.ofNullable(amazonSimpleEmailServiceFactoryReplacer).orElse(amazonSimpleEmailServiceFactory)
        );
    }

    public SendCompanyOwnEmailService getSendCompanyOwnEmailService()
    {
        return getSendCompanyOwnEmailService(null, null);
    }

    public SendCompanyOwnEmailService getSendCompanyOwnEmailService(
        SesService sesServiceReplacer,
        PebbleTemplateService pebbleTemplateServiceReplacer
    )
    {
        return new SendCompanyOwnEmailService(
            Optional.ofNullable(sesServiceReplacer).orElse(sesService),
            Optional.ofNullable(pebbleTemplateServiceReplacer).orElse(pebbleTemplateService)
        );
    }

    public OwnRequestService getOwnRequestService()
    {
        return getOwnRequestService(null, null, null, null, null, null, null, null, null);
    }

    public OwnRequestService getOwnRequestService(
        PersistenceLogService persistenceLogServiceReplacer,
        SecureRandomService secureRandomServiceReplacer,
        SendCompanyOwnEmailService sendCompanyOwnEmailServiceReplacer,
        CompanyOwnersService companyOwnersServiceReplacer,
        CompanyService companyServiceReplacer,
        UrlService urlServiceReplacer,
        HttpExceptionService httpExceptionServiceReplacer,
        JooqService jooqServiceReplacer,
        CompanyOwnRequestRepository companyOwnRequestRepositoryReplacer
    )
    {
        return new OwnRequestService(
            Optional.ofNullable(persistenceLogServiceReplacer).orElse(persistenceLogService),
            Optional.ofNullable(secureRandomServiceReplacer).orElse(secureRandomService),
            Optional.ofNullable(sendCompanyOwnEmailServiceReplacer).orElse(sendCompanyOwnEmailService),
            Optional.ofNullable(companyOwnersServiceReplacer).orElse(companyOwnersService),
            Optional.ofNullable(companyServiceReplacer).orElse(companyService),
            Optional.ofNullable(urlServiceReplacer).orElse(urlService),
            Optional.ofNullable(httpExceptionServiceReplacer).orElse(httpExceptionService),
            Optional.ofNullable(jooqServiceReplacer).orElse(jooqService),
            Optional.ofNullable(companyOwnRequestRepositoryReplacer).orElse(companyOwnRequestRepository)
        );
    }

    public PebbleTemplateService getPebbleTemplateService()
    {
        return getPebbleTemplateService(null);
    }

    public PebbleTemplateService getPebbleTemplateService(PebbleTemplateFactory pebbleTemplateFactoryReplacer)
    {
        return new PebbleTemplateService(
            Optional.ofNullable(pebbleTemplateFactoryReplacer).orElse(pebbleTemplateFactory)
        );
    }
}
