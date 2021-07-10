package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.repositories.GroupRepository;
import com.kbalazsworks.stackjudge.domain.repositories.ReviewRepository;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.company_services.SearchService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceFactory
{
    private final ApplicationProperties applicationProperties;
    private final DateFactory           dateFactory;
    private final SystemFactory         systemFactory;
    private final JwtFactory            jwtFactory;
    private final LocalDateTimeFactory  localDateTimeFactory;

    private final AddressService           addressService;
    private final SearchService            searchService;
    private final GroupService             groupService;
    private final ReviewService            reviewService;
    private final PaginatorService         paginatorService;
    private final JwtSubService            jwtSubService;
    private final DateTimeFormatterService dateTimeFormatterService;
    private final JooqService              jooqService;
    private final CdnService               cdnService;
    private final AccountService           accountService;

    private final CompanyRepository companyRepository;
    private final ReviewRepository  reviewRepository;
    private final GroupRepository   groupRepository;
    private final S3Repository      s3Repository;


    public CompanyService getCompanyService()
    {
        return getCompanyService(null, null, null, null, null, null, null, null);
    }

    public CompanyService getCompanyService(
        AddressService addressServiceReplacer,
        SearchService searchServiceReplacer,
        ReviewService reviewServiceReplacer,
        PaginatorService paginatorServiceReplacer,
        JooqService jooqServiceReplacer,
        CdnService cdnServiceReplacer,
        AccountService accountServiceReplaces,
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
            Optional.ofNullable(applicationPropertiesReplacer).orElse(applicationProperties),
            Optional.ofNullable(dateFactoryReplacer).orElse(dateFactory),
            Optional.ofNullable(systemFactoryReplacer).orElse(systemFactory),
            Optional.ofNullable(jwtSubServiceReplacer).orElse(jwtSubService)
        );
    }

    public JwtSubService getJwtSubService()
    {
        return new JwtSubService(applicationProperties, jwtFactory);
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
        return new ReviewService(reviewRepository);
    }

    public ReviewService getReviewService(ReviewRepository reviewRepositoryReplacer)
    {
        return new ReviewService(Optional.ofNullable(reviewRepositoryReplacer).orElse(reviewRepository));
    }

    public GroupService getGroupService()
    {
        return new GroupService(groupRepository);
    }

    public GroupService getGroupService(GroupRepository groupRepositoryReplacer)
    {
        return new GroupService(Optional.ofNullable(groupRepositoryReplacer).orElse(groupRepositoryReplacer));
    }

    public SearchService getSearchService()
    {
        return new SearchService(getGroupService());
    }

    public SearchService getSearchService(GroupService groupServiceReplacer)
    {
        return new SearchService(Optional.ofNullable(groupServiceReplacer).orElse(groupService));
    }

    public CdnService getCdnService()
    {
        return new CdnService(applicationProperties, localDateTimeFactory, dateTimeFormatterService, s3Repository);
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
}
