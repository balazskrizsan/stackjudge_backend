package com.kbalazsworks.stackjudge.domain.services;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.kbalazsworks.stackjudge.api.enums.CompanyRequestRelationsEnum;
import com.kbalazsworks.stackjudge.api.services.RestResponseEntityExceptionService;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.domain.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import org.jooq.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService
{
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private CompanyRepository companyRepository;
    private AddressService    addressService;
    private GroupService      groupService;
    private PaginatorService  paginatorService;
    private JooqService       jooqService;
    private CdnService        cdnService;

    @Autowired
    public void setJooqService(JooqService jooqService)
    {
        this.jooqService = jooqService;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setAddressService(AddressService addressService)
    {
        this.addressService = addressService;
    }

    @Autowired
    public void setStackService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @Autowired
    public void setPaginatorService(PaginatorService paginatorService)
    {
        this.paginatorService = paginatorService;
    }

    @Autowired
    public void setCdnService(CdnService cdnService)
    {
        this.cdnService = cdnService;
    }

    public void delete(long companyId)
    {
        companyRepository.delete(companyId);
    }

    public Company get(long companyId) throws RepositoryNotFoundException
    {
        return companyRepository.get(companyId);
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
                default -> companyRepository.search(seekId, limit);
            };
    }

    // todo: mock test
    public CompanySearchServiceResponse search(
        long seekId,
        int limit,
        List<Short> requestRelationIds,
        NavigationEnum navigation
    )
    {
        List<Company> companies = search(seekId, limit, navigation);

        Map<Long, CompanyStatistic> companyStatistics = new HashMap<>();
        List<PaginatorItem>         paginator         = new ArrayList<>();
        Long                        newSeekId         = null;
        if (requestRelationIds != null)
        {
            List<Long> companyIds = companies.stream().map(Company::id).collect(Collectors.toList());

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.STATISTIC.getValue()))
            {
                companyStatistics = getStatistic(companyIds);
            }
            if (requestRelationIds.contains(CompanyRequestRelationsEnum.PAGINATOR.getValue()))
            {
                newSeekId = companies.get(0).id();
                paginator = paginatorService.generate(
                    countRecordsBeforeId(newSeekId),
                    countRecords(),
                    limit
                );
            }
        }

        return new CompanySearchServiceResponse(companies, paginator, newSeekId, companyStatistics);
    }

    //@todo: test
    public long countRecords()
    {
        return companyRepository.countRecords();
    }

    //@todo: test
    public long countRecordsBeforeId(long seekId)
    {
        return companyRepository.countRecordsBeforeId(seekId);
    }

    // todo: mock test
    public Map<Long, CompanyStatistic> getStatistic(List<Long> companyIds)
    {
        Map<Long, CompanyStatistic> companyStatistics = new HashMap<>();

        Map<Long, Integer> stackInfo = groupService.countStacks(companyIds);
        Map<Long, Integer> teamInfo  = groupService.countTeams(companyIds);

        companyIds.forEach(id -> companyStatistics.put(id, new CompanyStatistic(
            id,
            stackInfo.getOrDefault(id, 0),
            teamInfo.getOrDefault(id, 0),
            0,
            0
        )));

        return companyStatistics;
    }

    public void create(Company company, Address address, MultipartFile companyLogo)
    {
        boolean success = jooqService.getDbContext().transactionResult(
            (Configuration config) ->
            {
                Long newId = companyRepository.create(company);
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
                        cdnService.put(CdnNamespaceEnum.COMPANY_LOGOS, newId + ".jpg", companyLogo);
                    }
                    catch (AmazonS3Exception e) //@todo: test
                    {
                        logger.error("Amazon S3 upload failed.", e);
                    }
                }

                return true;
            }
        );

        if (!success) //@todo: test
        {
            throw new CompanyHttpException(ExceptionResponseInfo.CompanyCreationFailedMsg)
                .withErrorCode(ExceptionResponseInfo.CompanyCreationFailedErrorCode)
                .withStatusCode(HttpStatus.BAD_REQUEST);
        }
    }
}
