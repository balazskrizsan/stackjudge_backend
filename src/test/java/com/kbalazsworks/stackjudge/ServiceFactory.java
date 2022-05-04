package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.entities.RegistrationSecret;
import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import com.kbalazsworks.stackjudge.api.services.FrontendUriService;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.RegistrationStateService;
import com.kbalazsworks.stackjudge.api.services.SpringCookieService;
import com.kbalazsworks.stackjudge.api.services.facebook_services.FacebookService;
import com.kbalazsworks.stackjudge.api.services.facebook_services.RegistrationAndLoginService;
import com.kbalazsworks.stackjudge.api.services.facebook_services.ScribeJavaFacebookService;
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
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.notification.push.PushToUserService;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.OpenSdkFileService;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.s3.upload.S3UploadApiService;
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
    private final ApplicationProperties       applicationProperties;
    private final DateFactory                 dateFactory;
    private final SystemFactory               systemFactory;
    private final JwtFactory                  jwtFactory;
    private final LocalDateTimeFactory        localDateTimeFactory;
    private final UrlFactory                  urlFactory;
    private final PebbleTemplateFactory       pebbleTemplateFactory;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;

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
    private final RegistrationStateService       registrationStateService;
    private final RegistrationAndLoginService    registrationAndLoginService;
    private final ScribeJavaFacebookService      scribeJavaFacebookService;
    private final JwtService                     jwtService;
    private final FrontendUriService             frontendUriService;
    private final SpringCookieService            springCookieService;
    private final PushToUserService              pushToUserService;

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
        AddressService addressServiceMock,
        SearchService searchServiceMock,
        ReviewService reviewServiceMock,
        PaginatorService paginatorServiceMock,
        JooqService jooqServiceMock,
        AccountService accountServiceReplaces,
        MapsService mapsServiceMock,
        CompanyOwnersService companyOwnersServiceMock,
        S3UploadApiService s3UploadApiServiceMock,
        OpenSdkFileService openSdkFileServiceMock,
        CompanyRepository companyRepositoryMock
    )
    {
        return new CompanyService(
            Optional.ofNullable(addressServiceMock).orElse(addressService),
            Optional.ofNullable(searchServiceMock).orElse(searchService),
            Optional.ofNullable(reviewServiceMock).orElse(reviewService),
            Optional.ofNullable(paginatorServiceMock).orElse(paginatorService),
            Optional.ofNullable(jooqServiceMock).orElse(jooqService),
            Optional.ofNullable(accountServiceReplaces).orElse(accountService),
            Optional.ofNullable(mapsServiceMock).orElse(mapsService),
            Optional.ofNullable(companyOwnersServiceMock).orElse(companyOwnersService),
            Optional.ofNullable(s3UploadApiServiceMock).orElse(s3UploadApiService),
            Optional.ofNullable(openSdkFileServiceMock).orElse(openSdkFileService),
            Optional.ofNullable(companyRepositoryMock).orElse(companyRepository)
        );
    }

    public JwtService getJwtService()
    {
        return getJwtService(null, null, null, null);
    }

    public JwtService getJwtService(
        ApplicationProperties applicationPropertiesMock,
        DateFactory dateFactoryMock,
        SystemFactory systemFactoryMock,
        JwtSubService jwtSubServiceMock
    )
    {
        return new JwtService(
            Optional.ofNullable(applicationPropertiesMock).orElse(ApplicationPropertiesMocker.getDefaultMock()),
            Optional.ofNullable(dateFactoryMock).orElse(dateFactory),
            Optional.ofNullable(systemFactoryMock).orElse(systemFactory),
            Optional.ofNullable(jwtSubServiceMock).orElse(jwtSubService)
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
        ApplicationProperties applicationPropertiesMock,
        JwtFactory jwtFactoryMock
    )
    {
        return new JwtSubService(
            Optional.ofNullable(applicationPropertiesMock).orElse(applicationProperties),
            Optional.ofNullable(jwtFactoryMock).orElse(jwtFactory)
        );
    }

    public ReviewService getReviewService()
    {
        return getReviewService(null);
    }

    public ReviewService getReviewService(ReviewRepository reviewRepositoryMock)
    {
        return new ReviewService(Optional.ofNullable(reviewRepositoryMock).orElse(reviewRepository));
    }

    public GroupService getGroupService()
    {
        return getGroupService(null);
    }

    public GroupService getGroupService(GroupRepository groupRepositoryMock)
    {
        return new GroupService(Optional.ofNullable(groupRepositoryMock).orElse(groupRepository));
    }

    public SearchService getSearchService()
    {
        return getSearchService(null);
    }

    public SearchService getSearchService(GroupService groupServiceMock)
    {
        return new SearchService(Optional.ofNullable(groupServiceMock).orElse(groupService));
    }

    public StaticProxyService getStaticProxyService()
    {
        return getStaticProxyService(null);
    }

    public StaticProxyService getStaticProxyService(ApplicationProperties applicationPropertiesMock)
    {
        return new StaticProxyService(
            Optional.ofNullable(applicationPropertiesMock).orElse(applicationProperties)
        );
    }

    public MapsService getMapsService()
    {
        return getMapsService(null, null, null, null, null, null, null);
    }

    public MapsService getMapsService(
        StateService stateServiceMock,
        StaticProxyService staticProxyServiceMock,
        GoogleStaticMapsCacheService staticMapsCacheServiceMock,
        MapMapperService mapMapperServiceMock,
        OpenSdkFileService openSdkFileServiceMock,
        S3UploadApiService s3UploadApiServiceMock,
        UrlFactory urlFactoryMock
    )
    {
        return new MapsService(
            Optional.ofNullable(stateServiceMock).orElse(MockFactory.getTestStateMock()),
            Optional.ofNullable(staticProxyServiceMock).orElse(staticProxyService),
            Optional.ofNullable(staticMapsCacheServiceMock).orElse(googleStaticMapsCacheService),
            Optional.ofNullable(mapMapperServiceMock).orElse(mapMapperService),
            Optional.ofNullable(openSdkFileServiceMock).orElse(openSdkFileService),
            Optional.ofNullable(s3UploadApiServiceMock).orElse(s3UploadApiService),
            Optional.ofNullable(urlFactoryMock).orElse(urlFactory)
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
        UsersRepository usersRepositoryMock,
        UserJooqRepository userJooqRepositoryReplaced,
        ProtectedReviewLogService protectedReviewLogServiceMock
    )
    {
        return new AccountService(
            Optional.ofNullable(usersRepositoryMock).orElse(usersRepository),
            Optional.ofNullable(userJooqRepositoryReplaced).orElse(userJooqRepository),
            Optional.ofNullable(protectedReviewLogServiceMock).orElse(protectedReviewLogService)
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
        PebbleTemplateService pebbleTemplateServiceMock
    )
    {
        return new CompanyOwnEmailService(
            Optional.ofNullable(pebbleTemplateServiceMock).orElse(pebbleTemplateService)
        );
    }

    public OwnRequestService getOwnRequestService()
    {
        return getOwnRequestService(null, null, null, null, null, null, null, null, null);
    }

    public OwnRequestService getOwnRequestService(
        PersistenceLogService persistenceLogServiceMock,
        SecureRandomService secureRandomServiceMock,
        CompanyOwnEmailService companyOwnEmailServiceMock,
        CompanyOwnersService companyOwnersServiceMock,
        CompanyService companyServiceMock,
        UrlService urlServiceMock,
        HttpExceptionService httpExceptionServiceMock,
        JooqService jooqServiceMock,
        CompanyOwnRequestRepository companyOwnRequestRepositoryMock
    )
    {
        return new OwnRequestService(
            Optional.ofNullable(persistenceLogServiceMock).orElse(persistenceLogService),
            Optional.ofNullable(secureRandomServiceMock).orElse(secureRandomService),
            Optional.ofNullable(companyOwnEmailServiceMock).orElse(companyOwnEmailService),
            Optional.ofNullable(companyOwnersServiceMock).orElse(companyOwnersService),
            Optional.ofNullable(companyServiceMock).orElse(companyService),
            Optional.ofNullable(urlServiceMock).orElse(urlService),
            Optional.ofNullable(httpExceptionServiceMock).orElse(httpExceptionService),
            Optional.ofNullable(jooqServiceMock).orElse(jooqService),
            Optional.ofNullable(companyOwnRequestRepositoryMock).orElse(companyOwnRequestRepository)
        );
    }

    public PebbleTemplateService getPebbleTemplateService()
    {
        return getPebbleTemplateService(null);
    }

    public PebbleTemplateService getPebbleTemplateService(PebbleTemplateFactory pebbleTemplateFactoryMock)
    {
        return new PebbleTemplateService(
            Optional.ofNullable(pebbleTemplateFactoryMock).orElse(pebbleTemplateFactory)
        );
    }

    public SlowServiceLoggerAspectService getSlowServiceLoggerAspectService()
    {
        return getSlowServiceLoggerAspectService(null);
    }

    public SlowServiceLoggerAspectService getSlowServiceLoggerAspectService(SystemFactory systemFactoryMock)
    {
        return new SlowServiceLoggerAspectService(
            Optional.ofNullable(systemFactoryMock).orElse(systemFactory)
        );
    }

    public SlowServiceLoggerAspect getSlowServiceLoggerAspect()
    {
        return getSlowServiceLoggerAspect(null);
    }

    public SlowServiceLoggerAspect getSlowServiceLoggerAspect(
        SlowServiceLoggerAspectService slowServiceLoggerAspectServiceMock
    )
    {
        return new SlowServiceLoggerAspect(
            Optional.ofNullable(slowServiceLoggerAspectServiceMock).orElse(slowServiceLoggerAspectService)
        );
    }

    public RegistrationStateService getRegistrationStateService()
    {
        return getRegistrationStateService(null, null);
    }

    public RegistrationStateService getRegistrationStateService(
        RegistrationSecretRepository registrationSecretRepositoryMock,
        RedisTemplate<String, RegistrationSecret> redisTemplateMock
    )
    {
        return new RegistrationStateService(
            Optional.ofNullable(registrationSecretRepositoryMock).orElse(registrationSecretRepository),
            Optional.ofNullable(redisTemplateMock).orElse(redisTemplateStringRegistrationSecret)
        );
    }

    public ProtectedReviewLogService getProtectedReviewLogService()
    {
        return getProtectedReviewLogService(null, null);
    }

    public ProtectedReviewLogService getProtectedReviewLogService(
        ProtectedReviewLogRepository protectedReviewLogRepositoryMock,
        CrudNotificationService crudNotificationServiceMock
    )
    {
        return new ProtectedReviewLogService(
            Optional.ofNullable(protectedReviewLogRepositoryMock).orElse(protectedReviewLogRepository),
            Optional.ofNullable(crudNotificationServiceMock).orElse(crudNotificationService)
        );
    }

    public FacebookService getFacebookService()
    {
        return getFacebookService(null, null, null, null, null, null, null);
    }

    public FacebookService getFacebookService(
        SecureRandomService secureRandomServiceMock,
        RegistrationStateService registrationStateServiceMock,
        OAuthFacebookServiceBuilder oAuthFacebookServiceBuilderMock,
        RegistrationAndLoginService registrationAndLoginServiceMock,
        JooqService jooqServiceMock,
        JwtService jwtServiceMock,
        SpringCookieService springCookieMock
    )
    {
        return new FacebookService(
            Optional.ofNullable(secureRandomServiceMock).orElse(secureRandomService),
            Optional.ofNullable(registrationStateServiceMock).orElse(registrationStateService),
            Optional.ofNullable(oAuthFacebookServiceBuilderMock).orElse(oAuthFacebookServiceBuilder),
            Optional.ofNullable(registrationAndLoginServiceMock).orElse(registrationAndLoginService),
            Optional.ofNullable(jooqServiceMock).orElse(jooqService),
            Optional.ofNullable(jwtServiceMock).orElse(jwtService),
            Optional.ofNullable(springCookieMock).orElse(springCookieService)
        );
    }

    public RegistrationAndLoginService getRegistrationAndLoginService()
    {
        return getRegistrationAndLoginService(null, null, null, null, null);
    }

    public RegistrationAndLoginService getRegistrationAndLoginService(
        AccountService accountServiceMock,
        ScribeJavaFacebookService scribeJavaFacebookServiceMock,
        FrontendUriService frontendUriServiceMock,
        PushToUserService pushToUserServiceMock,
        ApplicationProperties applicationPropertiesMock
    )
    {
        return new RegistrationAndLoginService(
            Optional.ofNullable(accountServiceMock).orElse(accountService),
            Optional.ofNullable(scribeJavaFacebookServiceMock).orElse(scribeJavaFacebookService),
            Optional.ofNullable(frontendUriServiceMock).orElse(frontendUriService),
            Optional.ofNullable(pushToUserServiceMock).orElse(pushToUserService),
            Optional.ofNullable(applicationPropertiesMock).orElse(ApplicationPropertiesMocker.getDefaultMock())
        );
    }

    public SpringCookieService getSpringCookieService()
    {
        return new SpringCookieService();
    }
}
