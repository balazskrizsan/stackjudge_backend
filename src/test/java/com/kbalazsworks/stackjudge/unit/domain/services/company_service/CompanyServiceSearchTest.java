package com.kbalazsworks.stackjudge.unit.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.fake_builders.*;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.*;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CompanyServiceSearchTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private record TestData(
        long testedSeekId,
        int testedLimit,
        List<Short> testedRequestRelationIds,
        NavigationEnum testedNavigation,
        List<Company> mockedCompanies,
        Map<Long, CompanyStatistic> mockForGetStatistic,
        List<PaginatorItem> mockForGenerate,
        Map<Long, List<Address>> mockForSearchAddresses,
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> mockForAddressMaps,
        Map<Long, Map<Long, List<Review>>> mockForReviews,
        Map<Long, User> mockForUsers,
        CompanySearchServiceResponse expectedResponse
    )
    {
    }

    private TestData provider(int repetition)
    {
        if (repetition == 1)
        {
            return new TestData(
                // tested
                1L,
                2,
                null,
                NavigationEnum.CURRENT_PLUS_1,
                // mock
                new CompanyFakeBuilder().buildAsList(),
                new HashMap<>(),
                new ArrayList<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                // expected
                new CompanySearchServiceResponse(
                    new CompanyFakeBuilder().buildAsList(),
                    new HashMap<>(),
                    new ArrayList<>(),
                    null,
                    new HashMap<>(),
                    new HashMap<>(),
                    new HashMap<>(),
                    new HashMap<>(),
                    new HashMap<>()
                )
            );
        }
        if (repetition == 2)
        {
            return new TestData(
                // tested
                1L,
                2,
                List.of((short) 1, (short) 2, (short) 3, (short) 4, (short) 5),
                NavigationEnum.CURRENT_PLUS_1,
                // mock
                new CompanyFakeBuilder().buildAsList(),
                Map.of(CompanyFakeBuilder.defaultId1, new CompanyStatisticFakeBuilder().build()),
                List.of(new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)),
                Map.of(CompanyFakeBuilder.defaultId1, new AddressFakeBuilder().buildAsList()),
                Map.of(CompanyFakeBuilder.defaultId1, Map.of(AddressFakeBuilder.defaultId1, new HashMap<>())),
                Map.of(
                    CompanyFakeBuilder.defaultId1,
                    Map.of(GroupFakeBuilder.defaultId1, new ReviewFakeBuilder().buildAsList())
                ),
                Map.of(UserFakeBuilder.defaultId1, new UserFakeBuilder().build()),
                // expected
                new CompanySearchServiceResponse(
                    new CompanyFakeBuilder().id(CompanyFakeBuilder.defaultId1).buildAsList(),
                    new HashMap<>(),
                    List.of(new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)),
                    CompanyFakeBuilder.defaultId1,
                    Map.of(CompanyFakeBuilder.defaultId1, new CompanyStatisticFakeBuilder().build()),
                    Map.of(CompanyFakeBuilder.defaultId1, new AddressFakeBuilder().buildAsList()),
                    Map.of(CompanyFakeBuilder.defaultId1, Map.of(AddressFakeBuilder.defaultId1, new HashMap<>())),
                    Map.of(
                        CompanyFakeBuilder.defaultId1,
                        Map.of(GroupFakeBuilder.defaultId1, new ReviewFakeBuilder().buildAsList())
                    ),
                    Map.of(UserFakeBuilder.defaultId1, new UserFakeBuilder().build())
                )
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
    public void allCallableCalled_byTheProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData   td                 = provider(repetitionInfo.getCurrentRepetition());
        List<Long> mockedCompaniesIds = td.mockedCompanies.stream().map(Company::id).collect(Collectors.toList());

        CompanyRepository companyRepositoryMock = MockCreator.getCompanyRepositoryMock();
        when(companyRepositoryMock.search(td.testedSeekId, td.testedNavigation, td.testedLimit))
            .thenReturn(td.mockedCompanies);
        when(companyRepositoryMock.countRecordsBeforeId(mockedCompaniesIds.get(0))).thenReturn(1L);
        when(companyRepositoryMock.countRecords()).thenReturn(2L);

        ReviewService reviewServiceMock = MockCreator.getReviewServiceMock();
        when(reviewServiceMock.search(mockedCompaniesIds)).thenReturn(td.mockForReviews);
        when(reviewServiceMock.maskProtectedReviewCreatedBys(td.mockForReviews))
            .thenReturn(td.mockForReviews);

        // Act
        CompanySearchServiceResponse actualResponse = serviceFactory.getCompanyService(
            AddressServiceMocks.search_returns_addressesMap(mockedCompaniesIds, td.mockForSearchAddresses),
            SearchServiceMocks.getStatistic_returns_statisticMap(mockedCompaniesIds, td.mockForGetStatistic),
            reviewServiceMock,
            PaginatorServiceMocks.generate_(1L, 2L, td.testedLimit, td.mockForGenerate),
            null,
            null,
            AccountServiceMocker.findByUserIdsWithIdMap_(List.of(UserFakeBuilder.defaultId1), td.mockForUsers),
            MapsServiceMocker.searchByAddresses_returns_addressMaps(td.mockForSearchAddresses, td.mockForAddressMaps),
            companyRepositoryMock
        )
            .search(td.testedSeekId, td.testedLimit, td.testedRequestRelationIds, td.testedNavigation);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(td.expectedResponse);
    }
}
