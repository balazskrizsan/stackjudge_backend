package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.entities.RegistrationSecret;
import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.RegistrationStateService;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.common.services.PaginatorService;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.address_module.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressService;
import com.kbalazsworks.stackjudge.domain.common_module.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.PebbleTemplateFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.common_module.repositories.CompanyOwnRequestRepository;
import com.kbalazsworks.stackjudge.domain.common_module.services.DateTimeFormatterService;
import com.kbalazsworks.stackjudge.domain.common_module.services.HttpExceptionService;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import com.kbalazsworks.stackjudge.domain.common_module.services.PebbleTemplateService;
import com.kbalazsworks.stackjudge.domain.common_module.services.UrlService;
import com.kbalazsworks.stackjudge.domain.company_module.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyOwnersService;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.company_module.services.OwnRequestService;
import com.kbalazsworks.stackjudge.domain.company_module.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.email_module.services.CompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.group_module.repositories.GroupRepository;
import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.domain.map_module.services.GoogleStaticMapsCacheService;
import com.kbalazsworks.stackjudge.domain.map_module.services.MapMapperService;
import com.kbalazsworks.stackjudge.domain.map_module.services.MapsService;
import com.kbalazsworks.stackjudge.domain.map_module.services.maps_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.notification_module.services.CrudNotificationService;
import com.kbalazsworks.stackjudge.domain.notification_module.services.notification_service.SearchMyNotificationsService;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.services.PersistenceLogService;
import com.kbalazsworks.stackjudge.domain.review_module.repositories.ProtectedReviewLogRepository;
import com.kbalazsworks.stackjudge.domain.review_module.repositories.ReviewRepository;
import com.kbalazsworks.stackjudge.domain.review_module.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.domain.review_module.services.ReviewService;
import com.kbalazsworks.stackjudge.domain_aspects.aspects.SlowServiceLoggerAspect;
import com.kbalazsworks.stackjudge.domain_aspects.services.SlowServiceLoggerAspectService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.ApplicationPropertiesMocker;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.services.OpenSdkFileService;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.s3.upload.S3UploadApiService;
import com.kbalazsworks.stackjudge.state.repositories.UserJooqRepository;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Service
@RequiredArgsConstructor
public class ServiceFactory
{
    private final ApplicationProperties applicationProperties;
    private final DateFactory           dateFactory;
    private final SystemFactory         systemFactory;
    private final JwtFactory            jwtFactory;
    private final LocalDateTimeFactory  localDateTimeFactory;
    private final UrlFactory            urlFactory;
    private final PebbleTemplateFactory pebbleTemplateFactory;

    private final AddressService                 addressService;
    private final SearchService                  searchService;
    private final GroupService                   groupService;
    private final ReviewService                  reviewService;
    private final PaginatorService               paginatorService;
    private final JwtSubService                  jwtSubService;
    private final DateTimeFormatterService       dateTimeFormatterService;
    private final JooqService                    jooqService;
    private final AccountService                 accountService;
    private final MapsService                    mapsService;
    private final StaticProxyService             staticProxyService;
    private final GoogleStaticMapsCacheService   googleStaticMapsCacheService;
    private final MapMapperService               mapMapperService;
    private final ProtectedReviewLogService      protectedReviewLogService;
    private final PebbleTemplateService          pebbleTemplateService;
    private final PersistenceLogService          persistenceLogService;
    private final SecureRandomService            secureRandomService;
    private final CompanyOwnEmailService         companyOwnEmailService;
    private final UrlService                     urlService;
    private final CompanyService                 companyService;
    private final HttpExceptionService           httpExceptionService;
    private final CompanyOwnersService           companyOwnersService;
    private final SlowServiceLoggerAspectService slowServiceLoggerAspectService;
    private final CrudNotificationService        crudNotificationService;
    private final S3UploadApiService             s3UploadApiService;
    private final OpenSdkFileService             openSdkFileService;

    private final CompanyRepository                         companyRepository;
    private final ReviewRepository                          reviewRepository;
    private final AddressRepository                         addressRepository;
    private final UsersRepository                           usersRepository;
    private final CompanyOwnRequestRepository               companyOwnRequestRepository;
    private final RegistrationSecretRepository              registrationSecretRepository;
    private final GroupRepository                           groupRepository;
    private final RedisTemplate<String, RegistrationSecret> redisTemplateStringRegistrationSecret;
    private final UserJooqRepository                        userJooqRepository;
    private final ProtectedReviewLogRepository              protectedReviewLogRepository;

    public CompanyService getCompanyService()
    {
        return getCompanyService(null, null, null, null, null, null, null, null, null, null, null);
    }

    public CompanyService getCompanyService(
        AddressService addressServiceReplacer,
        SearchService searchServiceReplacer,
        ReviewService reviewServiceReplacer,
        PaginatorService paginatorServiceReplacer,
        JooqService jooqServiceReplacer,
        AccountService accountServiceReplaces,
        MapsService mapsServiceReplacer,
        CompanyOwnersService companyOwnersServiceReplacer,
        S3UploadApiService s3UploadApiServiceReplacer,
        OpenSdkFileService openSdkFileServiceReplacer,
        CompanyRepository companyRepositoryReplacer
    )
    {
        return new CompanyService(
            Optional.ofNullable(addressServiceReplacer).orElse(addressService),
            Optional.ofNullable(searchServiceReplacer).orElse(searchService),
            Optional.ofNullable(reviewServiceReplacer).orElse(reviewService),
            Optional.ofNullable(paginatorServiceReplacer).orElse(paginatorService),
            Optional.ofNullable(jooqServiceReplacer).orElse(jooqService),
            Optional.ofNullable(accountServiceReplaces).orElse(accountService),
            Optional.ofNullable(mapsServiceReplacer).orElse(mapsService),
            Optional.ofNullable(companyOwnersServiceReplacer).orElse(companyOwnersService),
            Optional.ofNullable(s3UploadApiServiceReplacer).orElse(s3UploadApiService),
            Optional.ofNullable(openSdkFileServiceReplacer).orElse(openSdkFileService),
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
        return new GroupService(Optional.ofNullable(groupRepositoryReplacer).orElse(groupRepository));
    }

    public SearchService getSearchService()
    {
        return getSearchService(null);
    }

    public SearchService getSearchService(GroupService groupServiceReplacer)
    {
        return new SearchService(Optional.ofNullable(groupServiceReplacer).orElse(groupService));
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
        return getMapsService(null, null, null, null, null, null, null);
    }

    public MapsService getMapsService(
        StateService stateServiceReplacer,
        StaticProxyService staticProxyServiceReplacer,
        GoogleStaticMapsCacheService staticMapsCacheServiceReplacer,
        MapMapperService mapMapperServiceReplacer,
        OpenSdkFileService openSdkFileServiceReplacer,
        S3UploadApiService s3UploadApiServiceReplacer,
        UrlFactory urlFactoryReplacer
    )
    {
        return new MapsService(
            Optional.ofNullable(stateServiceReplacer).orElse(MockFactory.getTestStateMock()),
            Optional.ofNullable(staticProxyServiceReplacer).orElse(staticProxyService),
            Optional.ofNullable(staticMapsCacheServiceReplacer).orElse(googleStaticMapsCacheService),
            Optional.ofNullable(mapMapperServiceReplacer).orElse(mapMapperService),
            Optional.ofNullable(openSdkFileServiceReplacer).orElse(openSdkFileService),
            Optional.ofNullable(s3UploadApiServiceReplacer).orElse(s3UploadApiService),
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
        return getAccountService(null, null, null);
    }

    public AccountService getAccountService(
        UsersRepository usersRepositoryReplacer,
        UserJooqRepository userJooqRepositoryReplaced,
        ProtectedReviewLogService protectedReviewLogServiceReplacer
    )
    {
        return new AccountService(
            Optional.ofNullable(usersRepositoryReplacer).orElse(usersRepository),
            Optional.ofNullable(userJooqRepositoryReplaced).orElse(userJooqRepository),
            Optional.ofNullable(protectedReviewLogServiceReplacer).orElse(protectedReviewLogService)
        );
    }

    public HttpExceptionService getHttpExceptionService()
    {
        return new HttpExceptionService();
    }

    public CompanyOwnEmailService getCompanyOwnEmailService()
    {
        return getCompanyOwnEmailService(null);
    }

    public CompanyOwnEmailService getCompanyOwnEmailService(
        PebbleTemplateService pebbleTemplateServiceReplacer
    )
    {
        return new CompanyOwnEmailService(
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
        CompanyOwnEmailService companyOwnEmailServiceReplacer,
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
            Optional.ofNullable(companyOwnEmailServiceReplacer).orElse(companyOwnEmailService),
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

    public SlowServiceLoggerAspectService getSlowServiceLoggerAspectService()
    {
        return getSlowServiceLoggerAspectService(null);
    }

    public SlowServiceLoggerAspectService getSlowServiceLoggerAspectService(SystemFactory systemFactoryReplacer)
    {
        return new SlowServiceLoggerAspectService(
            Optional.ofNullable(systemFactoryReplacer).orElse(systemFactory)
        );
    }

    public SlowServiceLoggerAspect getSlowServiceLoggerAspect()
    {
        return getSlowServiceLoggerAspect(null);
    }

    public SlowServiceLoggerAspect getSlowServiceLoggerAspect(
        SlowServiceLoggerAspectService slowServiceLoggerAspectServiceReplacer
    )
    {
        return new SlowServiceLoggerAspect(
            Optional.ofNullable(slowServiceLoggerAspectServiceReplacer).orElse(slowServiceLoggerAspectService)
        );
    }

    public RegistrationStateService getRegistrationStateService()
    {
        return getRegistrationStateService(null, null);
    }

    public RegistrationStateService getRegistrationStateService(
        RegistrationSecretRepository registrationSecretRepositoryReplacer,
        RedisTemplate<String, RegistrationSecret> redisTemplateReplacer
    )
    {
        return new RegistrationStateService(
            Optional.ofNullable(registrationSecretRepositoryReplacer).orElse(registrationSecretRepository),
            Optional.ofNullable(redisTemplateReplacer).orElse(redisTemplateStringRegistrationSecret)
        );
    }

    public ProtectedReviewLogService getProtectedReviewLogService()
    {
        return getProtectedReviewLogService(null, null);
    }

    public ProtectedReviewLogService getProtectedReviewLogService(
        ProtectedReviewLogRepository protectedReviewLogRepositoryReplacer,
        CrudNotificationService crudNotificationServiceReplacer
    )
    {
        return new ProtectedReviewLogService(
            Optional.ofNullable(protectedReviewLogRepositoryReplacer).orElse(protectedReviewLogRepository),
            Optional.ofNullable(crudNotificationServiceReplacer).orElse(crudNotificationService)
        );
    }
}
