package com.kbalazsworks.stackjudge.domain.company_module.services;

import com.kbalazsworks.stackjudge.api.request_enums.CompanyRequestRelationsEnum;
import com.kbalazsworks.stackjudge.common.services.PaginatorService;
import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressService;
import com.kbalazsworks.stackjudge.domain.aws_module.enums.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.company_module.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.domain.company_module.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.company_module.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyGetServiceResponse;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroupTree;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.services.MapsService;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import com.kbalazsworks.stackjudge.domain.paginator_module.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.review_module.services.ReviewService;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.services.OpenSdkFileService;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.s3.upload.S3UploadApiService;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostUploadRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService
{
    private final AddressService       addressService;
    private final SearchService        searchService;
    private final ReviewService        reviewService;
    private final PaginatorService     paginatorService;
    private final JooqService          jooqService;
    private final AccountService       accountService;
    private final MapsService          mapsService;
    private final CompanyOwnersService companyOwnersService;
    private final S3UploadApiService   s3UploadApiService;
    private final OpenSdkFileService   openSdkFileService;
    private final CompanyRepository    companyRepository;

    public void delete(long companyId)
    {
        companyRepository.delete(companyId);
    }

    public Company get(long companyId)
    {
        return get(companyId, List.of()).company();
    }

    public CompanyGetServiceResponse get(long companyId, List<Short> requestRelationIds)
    throws RepositoryNotFoundException
    {
        CompanySearchServiceResponse searchResponse = search(
            companyId,
            1,
            requestRelationIds,
            NavigationEnum.EXACTLY_ONE_RECORD
        );

        return new CompanyGetServiceResponse(
            searchResponse.companies().get(0),
            searchResponse.companyStatistics().get(companyId),
            searchResponse.companyGroups().get(companyId),
            searchResponse.companyAddresses().get(companyId),
            searchResponse.companyAddressMaps().get(companyId),
            searchResponse.companyReviews().get(companyId),
            searchResponse.companyOwners().get(companyId),
            searchResponse.companyUsers()
        );
    }

    public List<Company> search(long seekId, int limit, NavigationEnum navigation)
    {
        if (navigation == null)
        {
            return companyRepository.search(seekId, limit);
        }

        return switch (navigation)
            {
                case FIRST -> companyRepository.search(0, limit);
                case LAST_MINUS_1, LAST, SECOND -> companyRepository.search(navigation, limit);
                case CURRENT_PLUS_1, CURRENT_PLUS_2, CURRENT_MINUS_1, CURRENT_MINUS_2 -> companyRepository.search(
                    seekId,
                    navigation,
                    limit
                );
                case EXACTLY_ONE_RECORD -> List.of(companyRepository.get(seekId));
                default -> companyRepository.search(seekId, limit);
            };
    }

    public CompanySearchServiceResponse search(
        long seekId,
        int limit,
        List<Short> requestRelationIds,
        NavigationEnum navigation
    )
    {
        List<Company> companies = search(seekId, limit, navigation);

        Map<Long, CompanyStatistic>                                   companyStatistics  = new HashMap<>();
        Map<Long, List<RecursiveGroupTree>>                           companyGroups      = new HashMap<>();
        List<PaginatorItem>                                           paginator          = new ArrayList<>();
        Long                                                          newSeekId          = null;
        Map<Long, CompanyAddresses>                                   companyAddresses   = new HashMap<>();
        Map<Long, Map<Long, List<Review>>>                            companyReviews     = new HashMap<>();
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> companyAddressMaps = new HashMap<>();
        Map<Long, User>                                               companyUsers       = new HashMap<>();
        Map<Long, CompanyOwners>                                      companyOwners      = new HashMap<>();
        List<Long>                                                    affectedUserIds    = new ArrayList<>();

        if (requestRelationIds != null)
        {
            List<Long> companyIds = companies.stream().map(Company::getId).collect(Collectors.toList());

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.STATISTIC.getValue()))
            {
                companyStatistics = searchService.getStatistic(companyIds);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.GROUP.getValue()))
            {
                companyGroups = searchService.getCompanyGroups(companyIds);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.PAGINATOR.getValue()))
            {
                // @todo: test the condition
                newSeekId = companies.isEmpty() ? 0 : companies.get(0).getId();
                paginator = paginatorService.generate(countRecordsBeforeId(newSeekId), countRecords(), limit);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.ADDRESS.getValue()))
            {
                companyAddresses = addressService.searchWithCompanyAddresses(companyIds);
            }

            if (!companyAddresses.isEmpty())
            {
                companyAddressMaps = mapsService.searchByAddresses(
                    companyAddresses.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        r -> r.getValue().addresses()
                    ))
                );
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.REVIEW.getValue()))
            {
                companyReviews = reviewService.maskProtectedReviewCreatedBys(reviewService.search(companyIds));

                companyReviews.forEach((companyId, items) ->
                    items.forEach((groupId, review) ->
                        affectedUserIds.addAll(review.stream().map(Review::createdBy).collect(Collectors.toList()))
                    )
                );
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.OWNER.getValue()))
            {
                companyOwners = companyOwnersService.searchWithCompanyIdGroupByCompany(companyIds);

                affectedUserIds.addAll(
                    companyOwners.values().stream().flatMap(r -> r.owners().stream()).collect(Collectors.toList())
                );
            }

            if (!affectedUserIds.isEmpty())
            {
                companyUsers = accountService.findByIdsWithIdMap(affectedUserIds);
            }
        }

        return new CompanySearchServiceResponse(
            companies,
            companyGroups,
            paginator,
            newSeekId,
            companyStatistics,
            companyAddresses,
            companyAddressMaps,
            companyReviews,
            companyOwners,
            companyUsers
        );
    }

    public long countRecords()
    {
        return companyRepository.countRecords();
    }

    public long countRecordsBeforeId(long seekId)
    {
        return companyRepository.countRecordsBeforeId(seekId);
    }

    public void create(@NonNull Company company, @NonNull Address address, MultipartFile companyLogo)
    {
        boolean success = jooqService.getDbContext().transactionResult(
            (Configuration config) ->
            {
                long newId = companyRepository.create(company);
                addressService.create(
                    new Address(
                        null,
                        newId,
                        address.fullAddress(),
                        address.markerLat(),
                        address.markerLng(),
                        address.manualMarkerLat(),
                        address.manualMarkerLng(),
                        address.createdAt(),
                        address.createdBy()
                    )
                );

                if (companyLogo != null && !companyLogo.isEmpty())
                {
                    try
                    {
                        PostUploadRequest request = new PostUploadRequest(
                            CdnNamespaceEnum.COMPANY_LOGOS.name(),
                            "",
                            String.valueOf(newId),
                            "jpg",
                            openSdkFileService.createByteArrayResourceEntityFromString(
                                companyLogo.getBytes(),
                                newId + ".jpg"
                            )
                        );

                        StdResponse<CdnServicePutResponse> apiResponse = s3UploadApiService
                            .execute(request);
                        updateLogoPath(newId, apiResponse.data().getPath());
                    }
                    catch (ResponseException e)
                    {
                        log.error("Amazon S3 upload failed.", e);
                    }
                }

                return true;
            }
        );

        if (!success) //@todo3: test
        {
            throw new CompanyHttpException(ExceptionResponseInfo.CompanyCreationFailedMsg)
                .withErrorCode(ExceptionResponseInfo.CompanyCreationFailedErrorCode)
                .withStatusCode(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateLogoPath(long companyId, String logoPath)
    {
        companyRepository.updateLogoPath(companyId, logoPath);
    }
}
