package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.SecureRandomService;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.factories.UrlFactory;
import com.kbalazsworks.stackjudge.domain.repositories.*;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.services.map_service.MapMapperService;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
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
    private final ApplicationProperties applicationProperties;
    private final DateFactory           dateFactory;
    private final SystemFactory         systemFactory;
    private final JwtFactory            jwtFactory;
    private final LocalDateTimeFactory  localDateTimeFactory;
    private final UrlFactory            urlFactory;

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

    private final CompanyRepository companyRepository;
    private final ReviewRepository  reviewRepository;
    private final GroupRepository   groupRepository;
    private final S3Repository      s3Repository;
    private final AddressRepository addressRepository;
    private final UsersRepository   usersRepository;


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
}
