package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.entities.RegistrationSecret;
import com.kbalazsworks.stackjudge.api.services.SpringCookieService;
import com.kbalazsworks.stackjudge.common.services.PaginatorService;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.address_module.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressService;
import com.kbalazsworks.stackjudge.domain.common_module.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.common_module.repositories.CompanyOwnRequestRepository;
import com.kbalazsworks.stackjudge.domain.common_module.services.DateTimeFormatterService;
import com.kbalazsworks.stackjudge.domain.common_module.services.HttpExceptionService;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
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
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids.account.ListService;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.OpenSdkFileService;
import com.kbalazsworks.stackjudge.state.repositories.UserJooqRepository;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.IS3Upload;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.ISesSendCompanyOwnEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceFactory
{
    private final ApplicationProperties applicationProperties;
    private final DateFactory           dateFactory;
    private final SystemFactory         systemFactory;
    private final LocalDateTimeFactory  localDateTimeFactory;
    private final UrlFactory            urlFactory;

    private final AddressService                 addressService;
    private final SearchService                  searchService;
    private final GroupService                   groupService;
    private final ReviewService                  reviewService;
    private final PaginatorService               paginatorService;
    private final DateTimeFormatterService       dateTimeFormatterService;
    private final JooqService                    jooqService;
    private final AccountService                 accountService;
    private final MapsService                    mapsService;
    private final StaticProxyService             staticProxyService;
    private final GoogleStaticMapsCacheService   googleStaticMapsCacheService;
    private final MapMapperService               mapMapperService;
    private final ProtectedReviewLogService      protectedReviewLogService;
    private final PersistenceLogService          persistenceLogService;
    private final SecureRandomService            secureRandomService;
    private final CompanyOwnEmailService         companyOwnEmailService;
    private final UrlService                     urlService;
    private final CompanyService                 companyService;
    private final HttpExceptionService           httpExceptionService;
    private final CompanyOwnersService           companyOwnersService;
    private final SlowServiceLoggerAspectService slowServiceLoggerAspectService;
    private final CrudNotificationService        crudNotificationService;
    private final IS3Upload                      s3UploadApiService;
    private final OpenSdkFileService             openSdkFileService;
    private final ISesSendCompanyOwnEmail        sesSendCompanyOwnEmailApiService;

    private final CompanyRepository                         companyRepository;
    private final ReviewRepository                          reviewRepository;
    private final AddressRepository                         addressRepository;
    private final UsersRepository                           usersRepository;
    private final CompanyOwnRequestRepository               companyOwnRequestRepository;
    private final GroupRepository                           groupRepository;
    private final RedisTemplate<String, RegistrationSecret> redisTemplateStringRegistrationSecret;
    private final UserJooqRepository                        userJooqRepository;
    private final ProtectedReviewLogRepository              protectedReviewLogRepository;
    private final ListService                               listService;

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
        IS3Upload s3UploadApiServiceMock,
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
        IS3Upload s3UploadApiServiceMock,
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
        UserJooqRepository userJooqRepositoryReplaced,
        ProtectedReviewLogService protectedReviewLogServiceMock,
        ListService listServiceMock
    )
    {
        return new AccountService(
            Optional.ofNullable(userJooqRepositoryReplaced).orElse(userJooqRepository),
            Optional.ofNullable(protectedReviewLogServiceMock).orElse(protectedReviewLogService),
            Optional.ofNullable(listServiceMock).orElse(listService)
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
        ISesSendCompanyOwnEmail sesSendCompanyOwnEmailApiServiceMock
    )
    {
        return new CompanyOwnEmailService(
            Optional.ofNullable(sesSendCompanyOwnEmailApiServiceMock).orElse(sesSendCompanyOwnEmailApiService)
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

    public SpringCookieService getSpringCookieService()
    {
        return new SpringCookieService();
    }
}
