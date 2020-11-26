package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.api.enums.CompanyRequestRelationsEnum;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.paginator.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService
{
    private CompanyRepository companyRepository;
    private AddressService    addressService;
    private GroupService      groupService;
    private PaginatorService  paginatorService;

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

    public void delete(long companyId)
    {
        companyRepository.delete(companyId);
    }

    public Company get(long companyId) throws RepositoryNotFoundException
    {
        return companyRepository.get(companyId);
    }

    public List<Company> search(long seekId, int limit)
    {
        return companyRepository.search(seekId, limit);
    }

    // todo: mock test
    public CompanySearchServiceResponse search(long seekId, int limit, List<Short> requestRelationIds)
    {
        List<Company> companies = search(seekId, limit);

        Map<Long, CompanyStatistic> companyStatistics = new HashMap<>();
        List<PaginatorItem>         paginator         = new ArrayList<>();
        if (requestRelationIds != null)
        {
            List<Long> companyIds = companies.stream().map(Company::id).collect(Collectors.toList());

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.STATISTIC.getValue()))
            {
                companyStatistics = getStatistic(companyIds);
            }
            if (requestRelationIds.contains(CompanyRequestRelationsEnum.PAGINATOR.getValue()))
            {
                paginator = paginatorService.generate(10L, 100, (short) 5);
            }
        }

        return new CompanySearchServiceResponse(companies, paginator, companyStatistics);
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

    public void create(Company company, Address address)
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
    }
}
